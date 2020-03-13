$(function(){

  if ( ! (window.File && window.FileReader && window.FileList && window.Blob)) {
    alert('Ваш браузер не поодерживает File APIs. Попробуйте другой браузер.');
  } else {
    $('#files').one('change', loadFile);
  };

});

let category;
let title;
let price;
let seller;
let description;
let link;

function loadFile(event) {
	$('#table').css("visibility", "visible");
  
	let files = event.target.files;

	for(let i = 0; i < files.length; i++) {
		let reader = new FileReader();
     	reader.readAsText(files[i]);
    	reader.onload = function (event) {
     		let content = event.target.result;
     		let contentToArray = content.split("\n");

     		for(let j = 0; j < contentToArray.length; j++) {
     			
     			if (j == 0 || (j % 6) == 0) {
     				category = files[i].name;
     				link = "avito.ru" + contentToArray[j];
     			};
     			if (j == 1 || ((j-1) % 6) == 0) {
     				title = contentToArray[j];
     			};
     			if (j == 2 || ((j-2) % 6) == 0) {
     				price = contentToArray[j];
     			};
     			if (j == 3 || ((j-3) % 6) == 0) {
     				seller = contentToArray[j];
     			};
     			if (j == 4 || ((j-4) % 6) == 0) {
     				description = contentToArray[j];
     				$("#table").append("<tr> <td>" + category + "</td> <td>" + title + "</td> <td>" + price + "</td> <td>" + seller + "</td> <td>" + description + "</td> <td><a target ='_blank' href='http://www." + link + "'>" + link + "</a></td> </tr>");
     			};

     		};

     		//console.log("File " + i);
     		//console.log(contentToArray);
   		};
   		reader.onerror = function(event) {
   			alert("Файл не может быть прочитан! Код ошибки " + event.target.error.code);
		};
	};
       
};