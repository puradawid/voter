package io.puradawid.voter;

import java.util.Date;

public interface Vote {

    Date arrived();

    Date declared();

    Listener voter();

    boolean positive();
}