var LocalEventProcessor = function () {
  var processor = this;
  this.storage = [];
  
  this.process = function (event) {
    processor.storage.push(event);
    console.log(processor.storage);
  };
};

var processor = new LocalEventProcessor();

var VoteEvent = function (context) { 
  this.timestamp = new Date();
};

var LikeItEvent = function (context) {
  VoteEvent.call(this, context);
  this.eventAction = "like";
};

var DislikeItEvent = function (context) {
  VoteEvent.call(this, context);
  this.eventAction = "dislike";
};

var processEvent = function(event) {
  processor.process(event);
};

var init = function (document) {
  var likeIt = document.querySelector('button.like-it'),
      dislikeIt = document.querySelector('button.dislike-it');
  
  likeIt.onclick = function (event) { 
    processEvent(new LikeItEvent(event));
  };

  dislikeIt.onclick = function (event) { 
    processEvent(new DislikeItEvent(event));
  };
};

window.onload = function () {
  init(document); 
};
