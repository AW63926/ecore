const tagAddButton = document.querySelector('.add-tag button');
const tagAddInput = document.querySelector('.add-tag input');
const tagsList = document.querySelector('.tags-list');
const documentContainer = document.querySelector('.container')
const needId = document.querySelector('.needId');

const xhr = new XMLHttpRequest()
xhr.onreadystatechange = function(){
    if(xhr.readyState === 4 && xhr.status === 200){
    const res = xhr.responseText;
    tagsList.innerHTML = res;
    }
}

tagAddButton.addEventListener('click', function(){
    postTags(tagAddInput.value, needId.value);
    console.log(tagAddInput.value);
    tagAddInput.value = "";
})

tagsList.addEventListener('click', function(event){
    if(event.target.classList.contains('x')){
    let tagId = event.target.previousElementSibling.previousElementSibling.value;
      console.log(tagId);
      removeTag(tagId);
    }
})


function postTags(tagName){
    const needIdToAdd= document.querySelector('.needId');
    xhr.open('POST', '/tags/' + tagName + '/' + needIdToAdd.value, true);
    xhr.send();
}

function removeTag(tagId){
    const needIdToRemove= document.querySelector('.needId');
    xhr.open('POST', '/tags/remove/' + tagId + '/' + needIdToRemove.value, true);
    xhr.send();
}
