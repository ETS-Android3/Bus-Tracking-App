const express = require('express');
const app = express();
const router = express.Router();

const path = __dirname;
const port = 5500;

router.use(function (req,res,next) {
  console.log('/' + req.method);
  next();
});

router.get('/home', function(req,res){
  res.sendFile(path + 'home.html');
})

router.get('/', function(req,res){
  res.sendFile(path + 'index.html');
});

router.get('/seeroute', function(req,res){
  res.sendFile(path + 'seeroute.html');
})

router.get('/news', function(req,res){
  res.sendFile(path + 'news.html');
})

app.use(express.static(path));
app.use('/', router);

app.listen(port, function () {
  console.log('Bus tracking app listening on port 5500!')
})