public class Content{
	private String title;
	private String type;
	private String size;
	Content(){
		this.title="";
		this.type="";
		this.size="";
	}
	public Content(String title,String type,String size){
		this.title=title;
		this.type=type;
		this.size=size;
	}
	public String getTitle(){
		return this.title;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type=type;
	}
	public String getSize(){
		return this.size;
	}
	public void setSize(String size){
		this.size=size;
	}
}