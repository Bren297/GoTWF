package graph;

public class LinkData {
	private int distanceCrossingLink;
	private int difficultyCrossingLink;
	private int dangerCrossingLink;
	private String linkName;

	/**
	 * Class constructor
	 * 
	 * @param distanceAcrossLink     The length value of the link
	 * @param difficultyCrossingLink The difficulty rating of the link
	 * @param dangerCrossingLink     How dangerous a link is
	 * @param linkName               The name of the link
	 */
	public LinkData(String linkName, int distanceCrossingLink, int difficultyCrossingLink, int dangerCrossingLink) {
		this.linkName = linkName;
		this.distanceCrossingLink = distanceCrossingLink;
		this.difficultyCrossingLink = difficultyCrossingLink;
		this.dangerCrossingLink = dangerCrossingLink;
	}

	// getters and setters
	public int getDistance() {
		return distanceCrossingLink;
	}

	public void setDistance(int distanceCrossingLink) {
		this.distanceCrossingLink = distanceCrossingLink;
	}

	public int getDifficulty() {
		return difficultyCrossingLink;
	}

	public void setDifficulty(int difficultyCrossingLink) {
		this.difficultyCrossingLink = difficultyCrossingLink;
	}

	public int getDanger() {
		return dangerCrossingLink;
	}

	public void setDanger(int dangerCrossingLink) {
		this.dangerCrossingLink = dangerCrossingLink;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	@Override
	public String toString() {
		return linkName;
	}

}