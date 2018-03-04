const express   = require('express')
const app       = express()
const https     = require('https')

var port = process.env.PORT || 3000;
var router = express.Router();

// middleware to use for all requests
router.use(function(req, res, next) {
    next(); // make sure we go to the next routes and don't stop here
});

router.route('/work')

    // get all the work (accessed at GET /work)
    .get(function(req, res) {
        getBlock(function(resp) {
            var block = JSON.parse(resp);
            console.log("Parsed block - " + block);
            res.json({
                "jobId" : 2, // for server use
                "clientId" : 5, // for server use, not processed by the device
                "blockHeader": block
            });
        });
    });

router.get('/', function(req, res) {
    res.json({ message: 'Not much going on here! Try /work' });
});

function getBlock(callback) {
    https.get(getBlockOptions(), function (res) {
        var responseString = "";

        res.on("data", function (data) {
            responseString += data;
        });
        res.on("end", function () {
            callback(responseString);
        });

    });
}

function getBlockOptions() {
    var id = Math.floor((Math.random() * 511400));
    var blockgetoptions = {
        host : 'bitaps.com',
        path : '/api/block/' + id,
        method : 'GET'
    };

    return blockgetoptions;
}

app.use('/', router);
app.listen(port);
console.log('Running on port ' + port);
