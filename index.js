let button = document.getElementById("dropdown");
let dropdown = document.getElementById("dropdownmenu");
button.addEventListener("click", ()=>
    dropdown.classList.toggle("active"));

let make = document.getElementById("modal__make");
let image = document.getElementById("modal__image");
let link = document.getElementsByClassName("publication__link");
let linkmake = document.getElementById("link__make");
let linkimage = document.getElementById("link__image");
for(i=0; i<link.length; i++){
    let data = link[i].getAttribute("data-id");
    link[i].addEventListener("click", ()=>{
        if(data=="1"&& !(make.classList.contains("open")&& !(linkmake.classList.contains("openlink")))){
            make.classList.toggle("open");
            linkmake.classList.toggle("openlink");
            image.classList.remove("open");
            linkimage.classList.remove("openlink");
        }
        else {
            image.classList.toggle("open");
            linkimage.classList.toggle("openlink");
            make.classList.remove("open");
            linkmake.classList.remove("openlink");
        }
    })
}

// resetlink(current)

let container = document.getElementById("principal");
fetch("https://data.snx.ovh/API.json")
.then(function(response){
    //On remonte les données au format json afin de pouvoir les traiter
    return response.json();
})
.then(response =>{
    console.log(response);
    for(i=0; i<response.length; i++){

        let post = document.createElement("article");
        post.classList.add("post");
        container.appendChild(post);

        let header = document.createElement("div");
        header.classList.add("post__header");
        post.appendChild(header);

        let author = document.createElement("h2");
        author.classList.add("post__author");
        author.textContent = "@" + response[i].author;
        header.appendChild(author);

        let image = document.createElement("img");
        image.classList.add("post__img");
        image.src = "assets/imageprofil.jpg";
        header.appendChild(image);

        let subtitle = document.createElement("p");
        subtitle.classList.add("post__subtitle");
        subtitle.textContent = response[i]["fullname"];
        header.appendChild(subtitle);

        let select = document.createElement("div");
        select.classList.add("post__select");
        select.id = "post__select";
        header.appendChild(select);

        let list = document.createElement("ul");
        list.classList.add("post__list");
        select.appendChild(list);

        let option1 = document.createElement("button");
        option1.classList.add("post__buttonselect");
        option1.id = "post__buttonselect";
        option1.textContent = "..."
        option1.addEventListener("click", ()=>{
            select.classList.toggle("openmenu");
        })
        header.appendChild(option1);

        let option2 = document.createElement("li");
        option2.classList.add("post__option");
        option2.classList.add("post__configuration");
        option2.textContent = "Configuration"
        list.appendChild(option2);

        let option3 = document.createElement("li");
        option3.classList.add("post__option");
        option3.textContent = "Save"
        list.appendChild(option3);

        let option4 = document.createElement("li");
        option4.classList.add("post__option");
        option4.textContent = "Hide"
        list.appendChild(option4);

        let option5 = document.createElement("li");
        option5.classList.add("post__option");
        option5.textContent = "Report"
        list.appendChild(option5);

        let corps = document.createElement("div");
        corps.classList.add("post__body");
        post.appendChild(corps);

        let datecontainer = document.createElement("div");
        datecontainer.classList.add("post__datecontainer");
        corps.appendChild(datecontainer)

        let icon = document.createElement("img");
        icon.classList.add("post__icon--clock");
        icon.src = "assets/Icon/clock.svg";
        datecontainer.appendChild(icon);

        let date = document.createElement("p");
        date.classList.add("post__date");
        date.textContent = response[i]["time"];
        datecontainer.appendChild(date);

        let title = document.createElement("h3");
        title.classList.add("post__title");
        title.textContent = response[i]["title"];
        corps.appendChild(title);

        let description = document.createElement("p");
        description.classList.add("post__description");
        description.textContent = response[i]["content"];
        corps.appendChild(description);

        let link = document.createElement("div");
        link.classList.add("post__link");
        post.appendChild(link);

        let like = document.createElement("a");
        like.classList.add("post__lien");
        like.textContent = "Like";
        link.appendChild(like);

        let likeicon = document.createElement("img");
        likeicon.classList.add("post__icon");
        likeicon.src = "assets/Icon/hand-thumbs-up.svg";
        like.appendChild(likeicon);

        let comment = document.createElement("a");
        comment.classList.add("post__lien");
        comment.textContent = "Comment";
        link.appendChild(comment);

        let commenticon = document.createElement("img");
        commenticon.classList.add("post__icon");
        commenticon.src = "assets/Icon/chat.svg";
        comment.appendChild(commenticon);

        let share = document.createElement("a");
        share.classList.add("post__lien");
        share.textContent = "Share";
        link.appendChild(share);

        let shareicon = document.createElement("img");
        shareicon.classList.add("post__icon");
        shareicon.src = "assets/Icon/share.svg";
        share.appendChild(shareicon);
    }
})