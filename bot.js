const eris = require('eris');
var fs = require('fs');

var output;
var nowWatching;


// Create a Client instance with our bot token.
const bot = new eris.Client('NzA1MzE0MjUyODQ3ODQxMzQw.Xqp6gA.v6rp2yEiTg8Xw32zU-gmDoSGlJg');

// When the bot is connected and ready, log to console.
bot.on('ready', () => {
   console.log('Connected and ready.');

});


// Every time a message is sent anywhere the bot is present,
// this event will fire and we will check if the bot was mentioned.
// If it was, the bot will attempt to respond with "Present".
bot.on('messageCreate', async (msg) => {

  const newMessage = msg.content;

  if (nowWatching == true) {
    fs.appendFile('displayApplication/newLog.txt', '\n' + newMessage, function (err) {
      if (err) throw err;
      console.log('Saved.');
    });
  }

  if (nowWatching != true && newMessage == "/./start logging") {
    nowWatching = true;
  }

  if (nowWatching == true && newMessage == "/./stop logging") {
    nowWatching = false;
    fs.unlink('displayApplication/src/newLog.txt', function (err) {
    if (err) throw err;
    console.log('File deleted!');
  });
  }


});

bot.on('error', err => {
   console.warn(err);
});

bot.connect()
