/**
 d.js html5 generation framework
 links
 button
 sfield
 label
 
 */

d=new function(){
this.extend=function(me,o){
	for(k in o){
		me[k]=o[k];
	}
};
var me=this;
this.extend(this,{
	links: {},
	t:function(tag, text, p){return "<"+tag+(p?' '+p:'')+">"+(text?text:'')+"</"+tag+">";	},
	button: function(value,onclick){
		me.links[value]=onclick;
		return '<input type="button" class="btn" name="'+value+'" value="'+value+'"/>';
	},
	addLink: function(){
		for(link in me.links){
			console.log(link);
			document.getElementsByName(link)[0].addEventListener("click", me.links[link]);
		}
	},
	sfield: function(field, input){
		return '<div class="centered"><label>'+field+': </label>'+
				'<select name="'+input+'"> <option>Human</option><option>Computer</option></select></div>';
	},
	label: function(title, value){
		return '<div class="centered"><label>'+title+': </label>'+
				'<span>'+value+' </span></div>';
	},
	pushForm: function(o){
		for(var key in o){
			var elts = document.getElementsByName(key);
			for(var i=0;i<elts.length;i++){
				elts[i].value=o[key];
			}
		}
	},
	pullForm: function(o){
		for(var key in o){
			var elts = document.getElementsByName(key);
			for(var i=0;i<elts.length;i++){
				o[key]=elts[i].value;
			}
		}
	}

		
		
});
return this;
}



