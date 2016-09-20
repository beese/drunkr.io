package drunkr;

public class QueryOptions {
	
	private String[] terms;
	private String[] filters;
	private String[] sortOptions;
	
	public QueryOptions(){}
	
	public String[] getTerms() {
		return terms;
	}
	
	public String[] getFilters() {
		return filters;
	}
	
	public String[] getSortOptions() {
		return sortOptions;
	}

}
