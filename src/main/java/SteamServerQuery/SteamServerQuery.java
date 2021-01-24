package SteamServerQuery;

import java.net.*;

public class SteamServerQuery {

	private InetAddress ServerAddress;
	private int ServerPort;
	private DatagramSocket UDPClient;

	public SteamServerQuery(InetAddress Address, int Port) {
		try {
			this.UDPClient = new DatagramSocket();
			this.ServerAddress = Address;
			this.ServerPort = Port;
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public SteamServerQuery(String Address, int Port) throws UnknownHostException {
		this(InetAddress.getByName(Address), Port);
	}

	public SteamServerQuery(String Address) throws UnknownHostException {
		this(Address.split(":")[0], Integer.parseInt(Address.split(":")[1]));
	}

	public DatagramSocket getDatagramSocket() {
		return UDPClient;
	}

	public SteamServerInfo getInfo() throws Exception {
		byte[] InfoHeader = new byte[25];
		InfoHeader[0] = (byte)0xFF;
		InfoHeader[1] = (byte)0xFF;
		InfoHeader[2] = (byte)0xFF;
		InfoHeader[3] = (byte)0xFF;
		InfoHeader[4] = (byte)0x54;
		byte[] SourceString = new String("Source Engine Query").getBytes();
		System.arraycopy(SourceString, 0, InfoHeader, 5, SourceString.length);
		InfoHeader[5 + SourceString.length] = (byte) 0x00;
		DatagramPacket SendInfoPacket = new DatagramPacket(InfoHeader, InfoHeader.length, this.ServerAddress, this.ServerPort);
		this.UDPClient.setSoTimeout(3000);
		this.UDPClient.send(SendInfoPacket);
		byte[] ReceivedBuffer = new byte[512];
		DatagramPacket ReceivedInfoPacket = new DatagramPacket(ReceivedBuffer, ReceivedBuffer.length);
		UDPClient.setSoTimeout(3000);
		this.UDPClient.receive(ReceivedInfoPacket);
		if (ReceivedBuffer[0] == (byte)0xFF && ReceivedBuffer[1] == (byte)0xFF && ReceivedBuffer[2] == (byte)0xFF && ReceivedBuffer[3] == (byte)0xFF && ReceivedBuffer[4] == SteamServerInfo.HEADER) {
			byte[] ServerInfoBuffer = new byte[ReceivedBuffer.length - 5];
			System.arraycopy(ReceivedBuffer, 5, ServerInfoBuffer, 0, ServerInfoBuffer.length);
			return new SteamServerInfo(ServerInfoBuffer);
		}
		return null;
	}

	public SteamServerPlayer getPlayer() throws Exception{
		byte[] PlayerHeader = this.getChallenge();
		PlayerHeader[4] = (byte)0x55;
		DatagramPacket SendPlayerPacket = new DatagramPacket(PlayerHeader, PlayerHeader.length, this.ServerAddress, this.ServerPort);
		this.UDPClient.send(SendPlayerPacket);
		byte[] ReceivedPlayerBuffer = new byte[1024];
		DatagramPacket ReceivedPlayerPacket = new DatagramPacket(ReceivedPlayerBuffer, ReceivedPlayerBuffer.length);
		this.UDPClient.setSoTimeout(3000);
		this.UDPClient.receive(ReceivedPlayerPacket);
		if (ReceivedPlayerBuffer[0] == (byte)0xFF && ReceivedPlayerBuffer[1] == (byte)0xFF && ReceivedPlayerBuffer[2] == (byte)0xFF && ReceivedPlayerBuffer[3] == (byte)0xFF && ReceivedPlayerBuffer[4] == SteamServerPlayer.HEADER) {
			byte[] ServerPlayerBuffer = new byte[ReceivedPlayerBuffer.length - 5];
			System.arraycopy(ReceivedPlayerBuffer, 5, ServerPlayerBuffer, 0, ServerPlayerBuffer.length);
			return new SteamServerPlayer(ServerPlayerBuffer);
		} else {
			System.err.println("ERROR Player Packet !");
			return null;
		}
	}

	public byte[] getChallenge() throws Exception {
		byte[] ChallengeHeader = new byte[9];
		ChallengeHeader[0] = (byte)0xFF;
		ChallengeHeader[1] = (byte)0xFF;
		ChallengeHeader[2] = (byte)0xFF;
		ChallengeHeader[3] = (byte)0xFF;
		ChallengeHeader[4] = (byte)0x55;
		ChallengeHeader[5] = (byte)0xFF;
		ChallengeHeader[6] = (byte)0xFF;
		ChallengeHeader[7] = (byte)0xFF;
		ChallengeHeader[8] = (byte)0xFF;

		DatagramPacket SendChallengePacket = new DatagramPacket(ChallengeHeader, ChallengeHeader.length, this.ServerAddress, this.ServerPort);
		this.UDPClient.send(SendChallengePacket);

		byte[] ReceivedChallengeBuffer = new byte[9];
		DatagramPacket ReceivedChallengePacket = new DatagramPacket(ReceivedChallengeBuffer, ReceivedChallengeBuffer.length);
		this.UDPClient.setSoTimeout(3000);
		this.UDPClient.receive(ReceivedChallengePacket);

		if(ReceivedChallengeBuffer[0] == (byte)0xFF && ReceivedChallengeBuffer[1] == (byte)0xFF && ReceivedChallengeBuffer[2] == (byte)0xFF && ReceivedChallengeBuffer[3] == (byte)0xFF && ReceivedChallengeBuffer[4] == SteamServerChallenge.HEADER) {
			return ReceivedChallengeBuffer;
		} else {
			System.err.println("ERROR Challenge Packet !");
			return new byte[9];
		}
	}
}
