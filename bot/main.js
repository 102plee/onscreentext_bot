const eris = require('eris');
const fs = require('fs');

const MESSAGE_FILE_PATH = "../messages.txt";

var nowWatching;

// Create a Client instance with our bot token.
const bot = new eris.Client(process.env.TOKEN);

// When the bot is connected and ready, log to console.
bot.on('ready', () => {
   console.log('Connected and ready.');
});

// Every time a message is sent anywhere the bot is present,
// this event will fire and we will check if the bot was mentioned.
// If it was, the bot will attempt to respond with "Present".
bot.on('messageCreate', async (msg) => {
  const newMessage = msg.content;
  var thischannel;

  // Start watching specified channel
  if (newMessage == "/./watch this channel") {
    thischannel = msg.channel_id;
  }

  if (msg.channel_id == thischannel) {

    // Three possible cases:
    // If nowWatching is true, log every new message.
    // If nowWatching is true and the command "/./stop logging" is sent,
    // stop logging.
    // If nowWatching is false, and the command "/./start logging" is sent,
    // start logging.

    if (nowWatching) {
      if (newMessage) {
        fs.appendFile(MESSAGE_FILE_PATH, '\n' + newMessage, function (err) {
          if (err) throw err;
          console.log('Saved.');
        });
      }
      if (newMessage === "/./stop logging") {
        nowWatching = false;
        fs.unlink(MESSAGE_FILE_PATH, function (err) {
          if (err) throw err;
          console.log('File deleted!');
        })
      }
    }

    if (nowWatching != true && newMessage == "/./start logging") {
      console.log('now watching');
      nowWatching = true;
    }
  }
});

bot.on('error', err => {
   console.warn(err);
});

bot.connect()
