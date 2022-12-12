export const mixin = {
	methods:{
		imageSrcformat(content,type){
			return 'data:image/'+type+';base64,'+content;
		}
	}
}