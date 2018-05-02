package io.puradawid.voter.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.puradawid.voter.Vote;
import io.puradawid.voter.VoteRepository;
import io.puradawid.voter.dto.VoteDto;
import io.puradawid.voter.dto.SavingResultDto;

class SaveToDiskRepository implements VoteRepository {

    private static final String CHECK_TABLE = "SELECT * FROM sqlite_master WHERE name = 'VOTES'";

    private static final String CREATE_TABLE =
        "CREATE TABLE VOTES("
            + "id integer primary key autoincrement,"
            + "arrived timestamp,"
            + "declared timestamp,"
            + "listenerId varchar(255),"
            + "positive bool);"
            + "PRAGMA votes.journal_mode = OFF";

    private static final String INSERT_VOTE =
        "INSERT INTO VOTES(arrived, declared, listenerId, positive) VALUES(?, ?, ?, ?)";

    private static final String SELECT_ALL = "SELECT * FROM votes";

    private final String name;

    SaveToDiskRepository(String name) {
        this.name = name;
    }

    private Connection useConnection() {
        try {
            return DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", name));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Connection loadOnce() {
        return useConnection();
    }

    private boolean createTable(Connection c) throws SQLException {
        return c.createStatement().execute(CREATE_TABLE);
    }

    private boolean init(Connection c) throws SQLException {
        ResultSet rs = c.createStatement().executeQuery(CHECK_TABLE);
        int rows = 0;
        while(rs.next()) {
            rows++;
        }
        if (rows == 0) {
            createTable(c);
            return init(c);
        }
        return true;
    }

    @Override
    public SavingResult save(Vote vote) {
        try {
            try (Connection c = loadOnce()) {
                if (!init(c)) {
                    throw new RuntimeException("Lack of table!");
                }
                PreparedStatement preparedStatement = c.prepareStatement(INSERT_VOTE);
                preparedStatement.setTimestamp(1, dateToIso(vote.arrived()));
                preparedStatement.setTimestamp(2, dateToIso(vote.declared()));
                preparedStatement.setString(3, vote.voter().id());
                preparedStatement.setBoolean(4, vote.positive());
                preparedStatement.execute();
                return new SavingResultDto(true);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private java.sql.Timestamp dateToIso(Date date) {
        return new java.sql.Timestamp(date.getTime());
    }

    @Override
    public List<Vote> all() {
        try {
            Connection c = loadOnce();
            if (!init(c)) {
                throw new RuntimeException("Lack of table");
            }
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(SELECT_ALL);
            List<Vote> result = new LinkedList<>();
            while (resultSet.next()) {
                Date arrived = resultSet.getDate("arrived");
                Date declared = resultSet.getDate("declared");
                String listenerId = resultSet.getString("listenerId");
                Boolean positive = resultSet.getBoolean("positive");
                result.add(new VoteDto(arrived, declared, listenerId, positive));
            }
            return result;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}