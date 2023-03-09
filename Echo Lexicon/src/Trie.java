import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;


public class Trie {

	public TrieNode root;
	private Random rand = new Random();
	public Trie() {
		root = new TrieNode();
	}
	char[] Word = new char[100];

	public void insert(String word,ArrayList<String> tag) {
		HashMap<Character, TrieNode> children = root.getChildren();
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			TrieNode node;
			if (children.containsKey(c)) {
				node = children.get(c);
			} else {
				node = new TrieNode(c);
				children.put(c, node);
			}
			children = node.getChildren();

			if (i == word.length() - 1) {
				node.setLeaf(true);
				node.setTag(tag);
			}
		}
	}

	public String search(String word,boolean returnAllTags,String priorityTag) {
		HashMap<Character, TrieNode> children = root.getChildren();

		TrieNode node = null;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (children.containsKey(c)) {
				node = children.get(c);
				children = node.getChildren();
			} else {
				node = null;
				break;
			}
		}
		String ret = new String();
		if (node != null && node.isLeaf()) {
			if(returnAllTags) {//if returnAllTags is flagged true list all of them.
				for(String curr:node.Tag) {
					ret = ret+curr+"%n";
				}
				return ret;
			}
			if(!priorityTag.equalsIgnoreCase("default")){//Prioritized Tagging. If it has it, it will return the priority tag
				if(node.Tag.contains(priorityTag)){
					ret = word+":";
					return ret;
				}
				else if(priorityTag.contains("|") && node.Tag.contains(priorityTag.substring(0,priorityTag.indexOf("|") ) ) ) {//If it doesnt have the exact tag does it at least have the person who said it the same
					ret = "---"+word+":"+priorityTag.substring(0,priorityTag.indexOf("|"));
					return ret;
				}
				
				
			}
			ret =  "---"+word+":"+node.Tag.get(rand.nextInt(node.tagCount));
			return ret;
		} else {
			ret ="\t"+word+":MISSING";
			return ret;
		}
	}
	public void SaveTrie(TrieNode root,PrintWriter out,int depth) {//Designing Basis for Saving.
		if(root.equals(null)) {
			return;
		}
		if(root.isLeaf()) {
			
			Word[depth]=root.c;
			//System.out.print(root.c);
			//System.out.print("   ");
			root.WriteNode(out,Word);
			Word[depth]='\0';
		}
		
		
		HashMap<Character, TrieNode> Children = root.getChildren();
		Set<Character> ChildArray = Children.keySet();
		for(char Cur : ChildArray) {
			if(depth>=0) {
				Word[depth]=root.c;
			}
			//System.out.print(root.c);
			SaveTrie(Children.get(Cur),out,depth+1);
			if(depth>=0) {
				Word[depth]='\0';
			}
		}
		
	}
}
