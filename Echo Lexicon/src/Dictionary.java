import java.io.PrintWriter;
import java.util.ArrayList;

public class Dictionary {
	Trie Lexicon = new Trie(); 
	
	
	//methods
	public void addWord(String Word,ArrayList<String> Tag) {
		
		Word=Word.toLowerCase();
		Lexicon.insert(Word,Tag);
	}
	public String searchWord(String Word) {
		return searchWord(Word, false,"default");
		
	}
	public String searchWord(String Word, boolean returnAllTags) {
		
		return searchWord(Word,returnAllTags,"default");
	}
	public String searchWord(String Word, String priorityTag) {
		return searchWord(Word, false, priorityTag);
		
	}
	public String searchWord(String Word,boolean returnAllTags,String priorityTag) {//Note if return AllTags is true then priority tag doesn't matter
		Word=Word.toLowerCase();
		return Lexicon.search(Word,returnAllTags, priorityTag);
		
	}
	
	public String tagPair(String ...tags) {
		String NewTag = new String();
		
		for(String current: tags) {
			NewTag = NewTag +" "+current;
		}
		return NewTag;
		
	}
	public void saveDict(PrintWriter out) {
		Lexicon.SaveTrie(Lexicon.root,out,-1);
	}
	
	
	
}
