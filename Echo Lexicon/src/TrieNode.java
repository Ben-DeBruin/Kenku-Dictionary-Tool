//import java.io.FileWriter;
//import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class TrieNode {
	public char c;
	private HashMap<Character, TrieNode> children = new HashMap<>();
	private boolean isLeaf;
	
	//My tag system
		public ArrayList<String> Tag= new ArrayList<String>();
		public int tagCount=0;

	public TrieNode() {}

	public TrieNode(char c){
		this.c = c;
	}

	public HashMap<Character, TrieNode> getChildren() {
		return children;
	}

	public void setChildren(HashMap<Character, TrieNode> children) {
		this.children = children;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public void setTag(ArrayList<String> tag) {
		
		for(String distinctTag: tag) {
			distinctTag= distinctTag.toLowerCase();
		if(!Tag.contains(distinctTag)) {
			Tag.add(distinctTag);
			tagCount++;
			
		}
		}
		return;
		
		
		
	}
	
	public void WriteNode(PrintWriter out,char Word[]) {
		
		//System.out.println("In WriteNode for char: "+c+" Tag Count:"+tagCount);
		
		for(String aTag:Tag) {
			out.println("["+aTag+"]");
			//System.out.println("Saving:"+aTag);
		}
		out.print("\"");
		for(char cur:Word) {
			if(cur=='\0') {
				break;
			}
			out.print(cur);
		}
		out.print("\"");
		out.println();
		
		
	}
}
