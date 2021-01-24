package SteamServerQuery;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SteamServerPlayer {
	private int position = 0;
	
	public static byte HEADER = (byte)0x44;
	
	public int PlayersLength;
	public ServerPlayer[] Players;
	
	public SteamServerPlayer(byte[] Buffer) {		
		this.PlayersLength = Buffer[this.position];		
		this.Players = new ServerPlayer[this.getPlayersLength()];
		
		this.position++;
		for (int i = 0; i < this.getPlayersLength(); i++) {			
			int PlayerIndex = Buffer[this.position];
			
			this.position++;
			int PlayerNameLength = getStringLenght(this.position, Buffer);
			byte[] PlayerName = new byte[PlayerNameLength];
			System.arraycopy(Buffer, this.position, PlayerName, 0, PlayerNameLength);
			
			
			this.position = this.position + PlayerNameLength + 1;
			long PlayerScore = ((Buffer[this.position + 3] & 0xFFL) << 24) | ((Buffer[this.position + 2] & 0xFFL) << 16) | ((Buffer[this.position + 1] & 0xFFL) <<  8) | ((Buffer[this.position + 0] & 0xFFL) <<  0);
			
			this.position = this.position + 4;
			float PlayerDuration = ByteBuffer.wrap(new byte[] {Buffer[this.position], Buffer[this.position+1], Buffer[this.position+2], Buffer[this.position+3]}).order(ByteOrder.LITTLE_ENDIAN).getFloat();
			
			this.position = this.position + 4;
			
			this.Players[i] = new ServerPlayer(PlayerIndex, new String(PlayerName), PlayerScore, PlayerDuration);
		}
	}
	
	public int getPlayersLength() {
		return this.PlayersLength;
	}
	
	public ServerPlayer[] getPlayers() {
		return this.Players;
	}
	
	private int getStringLenght(int start, byte[] buffer) {
		for (int i = start; i < buffer.length; i++) {
			if (buffer[i] == 0)
				return i - start;
		}
		
		return 0;
	}

	public List<Map<String,Object>> playerList (){
		List<Map<String,Object>> listMap = new ArrayList<>();
		for (ServerPlayer Player : this.getPlayers()) {
			Map<String,Object> map = new HashMap<>();
			map.put("playerName",Player.getName() );
			map.put("Score",  Long.parseLong(String.valueOf(Player.getScore())));
			map.put("Duration",Math.round(Player.getDuration() / 3600) + ":" + Math.round((Player.getDuration() % 3600) / 60) + ":" + Math.round((Player.getDuration() % 3600) % 60));
			listMap.add(map);
		}
		return listMap;
	}

	public String toString() {
		String PlayerTable = "";
		
		for (ServerPlayer Player : this.getPlayers()) {			
			PlayerTable += Player.getName() + ((Player.getName().length() <= 7) ? "\t\t\t\t" : ((Player.getName().length() <= 15) ? "\t\t\t" : ((Player.getName().length() <= 23) ? "\t\t" : "\t")));
			PlayerTable += new Long(Player.getScore()).intValue() + "\t\t";
			PlayerTable += Math.round(Player.getDuration() / 3600) + ":" + Math.round((Player.getDuration() % 3600) / 60) + ":" + Math.round((Player.getDuration() % 3600) % 60) + "\n";
		}
		
		return "Players : " + this.getPlayersLength() + "\nPlayer Name :\t\t\tScore :\t\tDuration:\n" + PlayerTable;
	}
}
