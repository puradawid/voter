/* Processor - events created
 *             are going to be stored */
var LocalEventProcessor = function () {
  var processor = this;
  this.storage = [];
  
  this.process = function (event) {
    processor.storage.push(event);
  };
};

var processor = new LocalEventProcessor();

/* Events - Vote event stamped with date
 *          and other context are included */
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

var init = function (document, processor) {
  var likeIt = document.querySelector('button.like-it'),
      dislikeIt = document.querySelector('button.dislike-it');
  
  likeIt.onclick = function (event) { 
    processor.process(new LikeItEvent(event));
  };

  dislikeIt.onclick = function (event) { 
    processor.process(new DislikeItEvent(event));
  };
};

window.onload = function () {
  init(document, processor); 
};
