const tagAddButton = document.querySelector('.addTag button');
const tagAddInput = document.querySelector('.addTag input');
const tagsList = document.querySelector('.tagsList');
const documentContainer = document.querySelector('.container');
const materialId = document.querySelector('.materialId');

const xhr = new XMLHttpRequest()
xhr.onreadystatechange = function(){
    if(xhr.readyState === 4 && xhr.status === 200){
        const res = xhr.responseText;
        tagsList.innerHTML = res;
    }
}

tagAddButton.addEventListener('click', function(){
    postTags(tagAddInput.value, materialId.value);
    console.log(tagAddInput.value);
    tagAddInput.value = "";

})

tagsList.addEventListener('click', function(event){
    if(event.target.classList.contains('x')){
        let tagId = event.target.previousElementSibling.previousE;ementsibling.value;
        console.log(tagId);
        removeTag(tagid);
    }
})

function postTags(tagName){
    const materialIdToadd = document.querySelector('.materialId');
    xhr.open('POST', '/material-tags/add/' + tagName + '/' + materialIdToadd.value, true);
    xhr.send();
}

function removeTag(tagId){
    const materialIdToRemove = document.querySelector('materialId');
    xhr.open('POST', '/material-tags/add/' + tagName + '/' + materialIdToRemove.value, true);
    xhr.send();
}