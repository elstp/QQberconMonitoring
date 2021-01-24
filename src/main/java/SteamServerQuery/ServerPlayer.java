package SteamServerQuery;

public class ServerPlayer {
	
	public int PlayerIndex;
	public String PlayerName;
	public long PlayerScore;
	public float PlayerDuration;
	
	public ServerPlayer(int Index, String Name, long Score, float Duration) {
		this.PlayerIndex = Index;
		this.PlayerName = Name;
		this.PlayerScore = Score;
		this.PlayerDuration = Duration;
	}
	
	public int getIndex() {
		return this.PlayerIndex;
	}
	
	public String getName() {
		return this.PlayerName;
	}
	
	public long getScore() {
		return this.PlayerScore;
	}
	
	public float getDuration() {
		return this.PlayerDuration;
	}
}
