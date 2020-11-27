function doGetComments() {


    sendCommentRequest(commentSuccessHandler, commentErrorHandler);
    let comments = JSON.parse(localStorage.getItem('comments'));
    let date = [];
    const container = document.getElementById('container');
    for (let index = comments.length - 1; index > -1; index--) {
        date[index] = "Published in: " + comments[index].creationTime.substring(0, 10)+ " at: " + comments[index].creationTime.substring(11,16);
        const card = document.createElement('div');
        card.classList = 'card-body';

        const content = `
        <div class="card">
        <div class="card-header" id="heading-${index}">
        </div>
          <div class="card-body">
            <h3>Updates</h3>
            <p>${date[index]}</p>
            <p>${comments[index].comment}</p>
          </div>
      </div>
      `;

        container.innerHTML += content;


    }

}

function commentSuccessHandler(responseData) {
    localStorage.removeItem('comments');
    localStorage.setItem('comments', JSON.stringify(responseData));
    console.log(responseData);
}

function commentErrorHandler(message, status) {
    localStorage.removeItem('comments');
    alert('Server: ' + message);
}
