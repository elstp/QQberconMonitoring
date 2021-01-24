package SteamServerQuery;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SteamServerInfo {
	private int position = 0;
	
	public static byte HEADER = (byte)0x49;
	private int ServerProtocol;
	private byte[] ServerName;
	private	byte[] ServerMap;
	private byte[] ServerFolder;
	private byte[] ServerGame;
	private short ServerAppID;
	private int ServerPlayers;
	private int ServerMaxPlayers;
	private int ServerBots;
	private int ServerType;
	private int ServerEnvironment;
	private int ServerVisibility;
	private int ServerVAC;
	private byte[] ServerVersion;
	private int ServerEDF;
	
	public SteamServerInfo(byte[] Buffer) {
		this.ServerProtocol = Buffer[0];
		
		this.position++;
		int ServerNameLength = this.getStringLenght(this.position, Buffer);
		this.ServerName = new byte[ServerNameLength];
		System.arraycopy(Buffer, this.position, this.ServerName, 0, ServerNameLength);
		
		this.position = this.position + ServerNameLength + 1;
		int ServerMapLength = this.getStringLenght(this.position, Buffer);
		this.ServerMap = new byte[ServerMapLength];
		System.arraycopy(Buffer, this.position, this.ServerMap, 0, ServerMapLength);
		
		this.position = this.position + ServerMapLength + 1;
		int ServerFolderLength = this.getStringLenght(this.position, Buffer);
		this.ServerFolder = new byte[ServerFolderLength];
		System.arraycopy(Buffer, this.position, this.ServerFolder, 0, ServerFolderLength);
		
		this.position = this.position + ServerFolderLength + 1;
		int ServerGameLength = this.getStringLenght(this.position, Buffer);
		this.ServerGame = new byte[ServerGameLength];
		System.arraycopy(Buffer, this.position, this.ServerGame, 0, ServerGameLength);
		
		this.position = this.position + ServerGameLength + 1;
		this.ServerAppID = ByteBuffer.wrap(Buffer, this.position, this.position + 1).order(ByteOrder.LITTLE_ENDIAN).getShort();
		
		this.position = this.position + 2;
		this.ServerPlayers = Buffer[this.position];
		
		this.position++;
		this.ServerMaxPlayers = Buffer[this.position];
		
		this.position++;
		this.ServerBots = Buffer[this.position];
		
		this.position++;
		this.ServerType = Buffer[this.position];
		
		this.position++;
		this.ServerEnvironment = Buffer[this.position];
		
		this.position++;
		this.ServerVisibility = Buffer[this.position];
		
		this.position++;
		this.ServerVAC = Buffer[this.position];
		
		this.position++;
		int ServerVersionLength = getStringLenght(this.position, Buffer);
		this.ServerVersion = new byte[ServerVersionLength];
		System.arraycopy(Buffer, this.position, this.ServerVersion, 0, ServerVersionLength);
		
		this.position = this.position + ServerVersionLength + 1;
		this.ServerEDF = Buffer[this.position];
	}
	
	public int getProtocol() {
		return this.ServerProtocol;
	}
	
	public String getName() {
		return new String(this.ServerName);
	}
	
	public String getMap() {
		return new String(this.ServerMap);
	}
	
	public String getFolder() {
		return new String(this.ServerFolder);
	}
	
	public String getGame() {
		return new String(this.ServerGame);
	}
	
	public short getAppID() {
		return this.ServerAppID;
	}
	
	public int getPlayers() {
		return this.ServerPlayers;
	}
	
	public int getMaxPlayers() {
		return this.ServerMaxPlayers;
	}
	
	public int getBots() {
		return this.ServerBots;
	}
	
	public int getType() {
		return this.ServerType;
	}
	
	public int getEnvironment() {
		return this.ServerEnvironment;
	}
	
	public int getVisibility() {
		return this.ServerVisibility;
	}
	
	public int getVAC() {
		return this.ServerVAC;
	}
	
	public String getVersion() {
		return new String(this.ServerVersion);
	}
	
	public int getEDF() {
		return this.ServerEDF;
	}
	
	private int getStringLenght(int start, byte[] buffer) {
		for (int i = start; i < buffer.length; i++) {
			if (buffer[i] == 0) {
				return i - start;
			}
		}
		
		return 0;
	}
	
	@Override
	public String toString() {
		return "Protocol : " + this.getProtocol() + "\nName : " + this.getName() + "\nMap : " + this.getMap() + "\nFolder : " + this.getFolder() + "\nGame : " + this.getGame() + "\nAppID : " + this.getAppID() + "\nPlayers : " + this.getPlayers() + "\nMax Players : " + this.getMaxPlayers() + "\nBots : " + this.getBots() + "\nServer Type : " + (char)this.getType() + " (d = DEDICATED|l = NON-DEDICATED|p = SourceTV/proxy)\nEnvironment : " + (char)this.getEnvironment() + " (l = Linux|w = Windows|m = MAC)\nVisibility : " + this.getVisibility() + " (0 = Public|1 = Private)\nVAC : " + this.getVAC() + " (0 = UNSECURED|1 = SECURED)\nVersion : " + this.getVersion() + "\nExtra Data Flag (EDF) : " + this.getEDF();
	}
}
