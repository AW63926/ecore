const tagAddButton = document.querySelector('.addTag button');
const tagAddInput = document.querySelector('addTag input');
const tagsLis = document.querySelector('tagsList');
const documentContainer = document.querySelector('.container');
const needId =document.querySelector('needId');

const xhr = new HMHLHttpRequest()
xhr.onreadystatechange = function(){
    if(xhr.readyState === 4 && xhr.status === 200){
        const res = xhr.responseText;
        tagsList.innerHTML = res;
    }
}

tagAddButton.addEventListener('click', function(){
    postTags(tagAddInput.nodeValue, needId.value);
    console.log(tagAddInput.value);
    tagAddInput.value = '';

})

tagsList.addEventListener('click', function(event){
    if(event.target.classList.contains('x')){
        let tagId = event.target.previousElementSibling.previousE;ementsibling.value;
        console.log(tagId);
        removeTag(tagid);
    }
})

function posttags(tagName){
    const materialIdToadd = document.querySelector('.materialId');
    xhr.open('POST', '/tags/' + tagName + '/' + materialIdToadd.value, true);
    xhr.send();
}

function removeTag(tagId){
    const materialIdToRemove = document.querySelector('materialId');
    xhr.open('POST', '/tags/' + tagName + '/' + materialIdToRemove.value, true);
    xhr.send();
}