/**
 * 
 */
package New6DigitBot;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author OtakuPug
 *
 */
public class bot extends ListenerAdapter {
	public static Member member;

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!(new File("codes.txt").exists())) {
			File codes = new File("codes.txt");
		}

		if (!(new File("logs.txt").exists())) {
			File codes = new File("logs.txt");
		}
		File code = new File("codes.txt");
		File log = new File("logs.txt");
		Message msg = e.getMessage(); // creating the message
		int IDs = 0;
		MessageChannel channel = e.getChannel(); // getting the channel

		boolean talkTest = msg.getContentRaw().startsWith("mtk") || msg.getContentRaw().startsWith("mtalk");
		boolean helpTest = msg.getContentRaw().startsWith("mhelp");
		boolean hugTest = msg.getContentRaw().startsWith("mhug");
		boolean sendTest = msg.getContentRaw().startsWith("msend") || msg.getContentRaw().startsWith("msd");
		boolean nhentaiTest = msg.getContentRaw().startsWith("mnh") || msg.getContentRaw().startsWith("mnhentai");
		boolean animeTest = msg.getContentRaw().startsWith("mani");
		
		
		try {
			logs(e, channel, msg, e.getGuild(), member);
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		if (nhentaiTest)
			try {
				sendNhentai(e, channel, msg);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		if (animeTest) {
			try {
				animeSearch(e, channel, msg);
			} catch (HttpStatusException e1) {
				e.getChannel().sendMessage("anime not available! please use the japanese version of the title! (yesterday wo utatte instead of sing \"yesterday\" for me)").queue();
				// TODO Auto-generated catch block
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (talkTest)
			talk(e, channel, msg);
		if (hugTest)
			hug(e, channel, msg.getContentRaw());
		if (sendTest)
			send(e, msg.getContentRaw());
		
		

		switch (msg.getContentRaw()) {

		case "mhelp":
			help(e, channel, msg);
			break;

		case "mhelp emotes":
			emotesHelp(e, channel, msg);
			break;

		case "mrh":
		case "mrandomnhentai":
			try {
				channel.sendMessage(sendRandomNhentai()).queue();
			} catch (IOException e2) {
				channel.sendMessage("this shouldn't happen! dm @OtakuPug#4791");
				e2.printStackTrace();
			}
			break;

		case "<@!635964555389960223>":
		case "<@635964555389960223>":
			channel.sendMessage("the prefix is m!").queue();
			break;

		case "mrch":
		case "mrandomcuratednhentai":
			try {
				sendRandomCuratedNhentai(e, channel);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;

		case "hello there":
		case "hewwo there":
		case "nagaping":
		case "melsaworry":
		case "02bounce":
		case "sataniaswim":
		case "ssw":
		case "sataniasmert":
		case "ssm":
		case "satanialaff":
		case "sl":
		case "sataniagudjob":
		case "sgj":
		case "sataniaevil":
		case "se":
		case "insomniahands":
		case "insomniathirst":
		case "phgLewd":
		case "phl":
		case "inh":
		case "ith":
		case "02b":
		case "nagap":
		case "mw":
			emoteGet(msg.getContentRaw(), e, channel);
			break;

		case "mhelp gifs":
			gifsHelp(e, channel, msg);
			break;

		case "gj":
			gj(e, channel, msg);
			break;

		case "sendcodes":
			channel.sendMessage("code list").addFile(code).queue();
			break;

		case "sendlogs":
			channel.sendMessage("logs").addFile(log).queue();
			break;

		case "mdl":
		case "mdelete":
			delete(e, channel);
			break;

		case "ggif":
		case "getguildinfo":
			getGuildInfo(e, channel);
			break;

		case "jar":
			try {
				shadowJar(channel);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		}
	}

	private void animeSearch(MessageReceivedEvent e, MessageChannel channel, Message msg) throws IOException {
		String animeName = msg.getContentRaw().substring(msg.getContentRaw().indexOf(" ") + 1).replace(" ", "-");
		String animePageUrl = "https://www2.gogoanime.video/category/" + animeName;
		//page info
		Document doc = Jsoup.connect(animePageUrl)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
			      .referrer("https://www2.gogoanime.video/bna-episode-1")
				.get();
		Scanner htmlScanner = new Scanner(doc.html());
		ArrayList<String> contentOfHtml = new ArrayList<String>();
		int i = 0;
		int actualIndex = 0;
		while(htmlScanner.hasNextLine()) {
			if(i > 147 && i < 173) {
				contentOfHtml.add(actualIndex + " " + htmlScanner.nextLine() + "\n");
				actualIndex++;
			}
			if(i < 147 || i > 173) htmlScanner.nextLine();
			i++;
		}
		
		String title = contentOfHtml.get(3).substring(contentOfHtml.get(3).indexOf(">"));
		String titleFinal = title.substring(1, title.indexOf("</"));
		
		String summary = contentOfHtml.get(6).substring(contentOfHtml.get(6).indexOf("</span>"));
		String summaryFinal = summary.substring(summary.indexOf("n>") + 2, summary.indexOf("</p>"));
		
		String coverImg = contentOfHtml.get(2).substring( contentOfHtml.get(2).indexOf("\"") + 1, contentOfHtml.get(2).indexOf("\"", contentOfHtml.get(2).indexOf("\"") + 1));
		
		//anime video
		
		String episodeAmount = contentOfHtml.get(24).substring(contentOfHtml.get(24).indexOf("ep_end="));
		String episodeAmountFinal = episodeAmount.substring(episodeAmount.indexOf("\"") + 1, episodeAmount.indexOf("\">"));
		
		String links = "";
		Integer episodeAmountInt = Integer.valueOf(episodeAmountFinal);
		for(int currentEp = 1; currentEp <= episodeAmountInt; currentEp++) {
			links += "[episode " + currentEp + "]" + "(" + getLink("https://www2.gogoanime.video/" + animeName + "-episode-" + currentEp) + ")" + "\n";
			
		}
		
		EmbedBuilder maniSearch = new EmbedBuilder();
		maniSearch.setTitle(titleFinal);
		maniSearch.setDescription(summaryFinal + "\n" + links);
		maniSearch.setColor(Color.pink);
		maniSearch.setThumbnail(coverImg);
		channel.sendMessage(maniSearch.build()).queue();
		// TODO Auto-generated method stub
		
	}

	private String getLink(String url) {
		try {
			Document doc2 = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				      .referrer("https://www2.gogoanime.video/bna-episode-1")
					.get();
			Scanner htmlScanner2 = new Scanner(doc2.html());
		ArrayList<String> contentOfHtml2 = new ArrayList<String>();
		
		int i = 0;
		while(htmlScanner2.hasNextLine()) contentOfHtml2.add(i++ + " " + htmlScanner2.nextLine() + "\n");
		//System.out.println("line199: " + contentOfHtml2.get(199));
		System.out.println("line201: " + contentOfHtml2.get(201));
		String link = contentOfHtml2.get(201);
		if(link.contains("\">")) {
			link = contentOfHtml2.get(201).substring(contentOfHtml2.get(201).indexOf("https")); 
		} else {
			link = contentOfHtml2.get(199).substring(contentOfHtml2.get(199).indexOf("https"));
		}
		
		
		String linkFinal = "";
		
			linkFinal = link.substring(0, link.indexOf("\">"));
		
		System.out.println("get link: " + linkFinal);
		return linkFinal;
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @param e
	 * @param channel
	 */
	private void getGuildInfo(MessageReceivedEvent e, MessageChannel channel) {
		EmbedBuilder ggif = new EmbedBuilder();
		ggif.setTitle(e.getGuild().getName());
		ggif.setDescription("Server Id: " + e.getGuild().getId() + "\nChannel Id: " + channel.getId() + "\nOwner Id: "
				+ e.getGuild().getOwnerId() + "\nTime Created: " + e.getGuild().getTimeCreated());
		ggif.setColor(Color.pink);
		ggif.setThumbnail(e.getGuild().getIconUrl());
		channel.sendMessage(ggif.build()).queue();
	}

	/**
	 * @param e
	 * @param channel
	 */
	private void delete(MessageReceivedEvent e, MessageChannel channel) {
		e.getMessage().delete().queue();
		String messageId = e.getMessageId();
		MessageHistory history = MessageHistory.getHistoryBefore(channel, messageId).limit(1).complete();
		channel.purgeMessages(history.getRetrievedHistory());
	}

	private void send(MessageReceivedEvent e, String contentRaw) {
		Scanner temp = new Scanner(contentRaw);
		String discard = temp.next();
		String id = temp.next();
		String[] temp2 = contentRaw.split(" ");
		String msg = "";
		for (int i = 2; i < temp2.length; i++) {
			msg += temp2[i] + " ";
		}
		e.getJDA().getTextChannelById(id).sendMessage(msg).queue();
	}

	private void logs(MessageReceivedEvent e, MessageChannel channel, Message msg, Guild guild, Member currentMember)
			throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter("logs.txt", true));
		pw.println("[Channel: " + channel + "]" + "[Server: " + guild + "]" + "[User: " + e.getAuthor().getAsMention()
				+ " | " + e.getAuthor().getAsTag() + "]" + "[Message: " + msg.getContentRaw() + "]");
		pw.flush();
		pw.close();

	}

	private void hug(MessageReceivedEvent e, MessageChannel channel, String msg) {
		ArrayList<String> gifs = new ArrayList<String>();
		gifs.add("https://media.giphy.com/media/TdXxcoNvHDVu0/giphy.gif");
		gifs.add("https://media.giphy.com/media/od5H3PmEG5EVq/giphy.gif");
		gifs.add("https://media.giphy.com/media/143v0Z4767T15e/giphy.gif");
		gifs.add("https://media.giphy.com/media/yziFo5qYAOgY8/giphy.gif");
		gifs.add("https://media.giphy.com/media/HaC1WdpkL3W00/giphy.gif");
		gifs.add("https://cdn.discordapp.com/attachments/662454580843380785/678431407785246749/tenor.gif");
		gifs.add("https://media.giphy.com/media/iMrHFdDEoxT5S/giphy.gif");
		gifs.add("https://media.giphy.com/media/BXrwTdoho6hkQ/giphy.gif");
		gifs.add("https://media.giphy.com/media/13YrHUvPzUUmkM/giphy.gif");

		try {
			EmbedBuilder hug = new EmbedBuilder();
			String author = e.getAuthor().getAsMention();
			hug.setDescription(author + " hugs " + msg.substring(5, msg.length()) + "!");
			hug.setColor(Color.pink);
			hug.setImage(gifs.get(ThreadLocalRandom.current().nextInt(0, gifs.size())));
			channel.sendMessage(hug.build()).queue();
			e.getMessage().delete().queue();
		} catch (StringIndexOutOfBoundsException e1) {
			channel.sendMessage("you need to @ someone!").queue();
		}

	}

	private void emoteGet(String contentRaw, MessageReceivedEvent e, MessageChannel channel) {
		switch (contentRaw) {
		case "hello there":
			e.getMessage().delete().queue();
			channel.sendMessage("<:HelloThere:618115969897988096>").queue();
			break;

		case "hewwo there":
			channel.sendMessage("<:HelloThere:618115969897988096>").queue();
			break;

		case "nagaping":
			e.getMessage().delete().queue();
			channel.sendMessage("<:nagaping:637871602259984401>").queue();
			break;

		case "melsaworry":
			e.getMessage().delete().queue();
			channel.sendMessage("<:MelsaWorry:628426584801542144>").queue();
			break;

		case "02bounce":
			e.getMessage().delete().queue();
			channel.sendMessage("<a:ezgif:637875460059037698>").queue();
			break;

		case "sataniaswim":
		case "ssw":
			e.getMessage().delete().queue();
			channel.sendMessage("<a:SataniaSwim:425158560515620864>").queue();
			break;

		case "sataniasmert":
		case "ssm":
			e.getMessage().delete().queue();
			channel.sendMessage("<a:SataniaSmert:425391062463283220>").queue();
			break;

		case "satanialaff":
		case "sl":
			e.getMessage().delete().queue();
			channel.sendMessage("<a:SataniaLaff:425140978744295434>").queue();
			break;

		case "sataniagudjob":
		case "sgj":
			e.getMessage().delete().queue();
			channel.sendMessage("<a:SataniaGudJob:425159186289000458>").queue();
			break;

		case "sataniaevil":
		case "se":
			e.getMessage().delete().queue();
			channel.sendMessage("<a:SataniaEvil:425141075502694411>").queue();
			break;

		case "insomniahands":
			e.getMessage().delete().queue();
			channel.sendMessage("<:insomniaHands:678410126927069205>").queue();
			break;
		case "insomniathirst":
			e.getMessage().delete().queue();
			channel.sendMessage("<:insomniaThirst:678410128340418560>").queue();
			break;
		case "phgLewd":
			e.getMessage().delete().queue();
			channel.sendMessage("<:phgLewd:678410126910291979>").queue();
			break;
		case "phl":
			e.getMessage().delete().queue();
			channel.sendMessage("<:phgLewd:678410126910291979>").queue();
			break;
		case "inh":
			e.getMessage().delete().queue();
			channel.sendMessage("<:insomniaHands:678410126927069205>").queue();
			break;
		case "ith":
			e.getMessage().delete().queue();
			channel.sendMessage("<:insomniaThirst:678410128340418560>").queue();
			break;
		case "02b":
			e.getMessage().delete().queue();
			channel.sendMessage("<a:ezgif:637875460059037698>").queue();
			break;
		case "nagap":
			e.getMessage().delete().queue();
			channel.sendMessage("<:nagaping:637871602259984401>").queue();
			break;
		case "mw":
			e.getMessage().delete().queue();
			channel.sendMessage("<:MelsaWorry:628426584801542144>").queue();
			break;
		}

	}

	private void shadowJar(MessageChannel channel) throws IOException {
		Runtime.getRuntime()
				.exec("cmd /c cd C:\\Users\\OtakuPug\\Documents\\Melsa\\New6DigitBot2 && gradlew shadowJar");
		Runtime.getRuntime().exec("cmd /c gradlew shadowJar");
		File jars = new File(
				"C:\\Users\\OtakuPug\\Documents\\Melsa\\New6DigitBot2\\build\\libs\\New6DigitBot2-1.0.jar");
		channel.sendMessage("jar binks").queue();

	}

	private void sendRandomCuratedNhentai(MessageReceivedEvent e, MessageChannel channel) throws IOException {

		Scanner scan = new Scanner(Paths.get("codes.txt"));
		int randnum = ThreadLocalRandom.current().nextInt(1, countLines(scan));
		channel.sendMessage("https://nhentai.net/g/" + search(randnum, scan) + "/").queue();

	}

	private String search(int randnum, Scanner scan) {
		// TODO Auto-generated method stub
		for (int i = 0; i < randnum; i++) {
			scan.next();
		}
		return scan.next();
	}

	private int countLines(Scanner scan) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("codes.txt"));
		int lines = 0;
		while (reader.readLine() != null)
			lines++;
		reader.close();
		return lines;
	}

	private static String sendRandomNhentai() throws IOException {
		while (true) {
			String link = "https://nhentai.net/g/" + ThreadLocalRandom.current().nextInt(1, 300000 + 1) + "/";
			if (checkLoliAndShota(link)) {
				return link;

			}

		}
	}

	private static boolean checkLoliAndShota(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements content = doc.select("a.tag.tag-19440");
		
		content.addAll(doc.select("a.tag.tag-32341"));
		String text = content.text();
		if (text.contains("lolicon") || text.contains("shotacon"))
			return false;
		else
			return true;
	}

	private void gj(MessageReceivedEvent e, MessageChannel channel, Message msg) {
		e.getMessage().delete().queue();
		EmbedBuilder ebgj = new EmbedBuilder();
		String author = e.getAuthor().getAsMention();
		ebgj.setDescription("from: " + author);
		ebgj.setColor(Color.green);
		ebgj.setImage("https://cdn.discordapp.com/emojis/395671317497511937.gif?v=1");

		channel.sendMessage(ebgj.build()).queue();

	}

	private void gifsHelp(MessageReceivedEvent e, MessageChannel channel, Message msg) {
		EmbedBuilder ebgifs = new EmbedBuilder();
		ebgifs.setTitle("List of Gifs");
		ebgifs.setColor(Color.pink);
		ebgifs.setThumbnail("https://cdn.discordapp.com/attachments/310633151875514368/637274476840747008/Melsa.png");
		ebgifs.setDescription("only gj works atm\ngj\ngacha\nnuuu\nbruh\nheccyeah");
		channel.sendMessage(ebgifs.build()).queue();

	}

	private void emotesHelp(MessageReceivedEvent e, MessageChannel channel, Message msg) {
		EmbedBuilder ebemotes = new EmbedBuilder();
		ebemotes.setTitle("List of Emotes");
		ebemotes.setColor(Color.pink);
		ebemotes.setThumbnail("https://cdn.discordapp.com/attachments/310633151875514368/637274476840747008/Melsa.png");
		ebemotes.setDescription("hello there\n" + "nnagaping\\nagap\n" + "melsaworry\\mw\n" + "02bounce\\02b\n"
				+ "sataniaswim\\ssw\n" + "sataniasmert\\ssm\n" + "satanialaff\\sl\n" + "sataniagudjob\\sgj\n"
				+ "sataniaevil\\se\n" + "insomniahands\\inh\n" + "insomniathirst\\ith\n" + "phgLewd\\phl");
		channel.sendMessage(ebemotes.build()).queue();

	}

	private void help(MessageReceivedEvent e, MessageChannel channel, Message msg) {
		EmbedBuilder ebhelp = new EmbedBuilder();
		ebhelp.setTitle("6DigitBot Commands");
		ebhelp.setColor(Color.pink);
		ebhelp.setThumbnail("https://cdn.discordapp.com/attachments/310633151875514368/637274476840747008/Melsa.png");
		ebhelp.setDescription("**mhelp <command>** for help on that specific command\n"
				+ "**mnhentai<digits>\\mnh<digits>** for an nhentai link and to add a doujin to the bot\n"
				+ "**mrandomnhentai\\mrh** for random code\n"
				+ "**mrandomcuratedhentai\\mrch** for a user submitted code\n"
				+ "**ggif\\getguildinfo** for info about the server\n"
				+ "**sendlogs** for log of all the servers this bot is in\n"
				+ "**sendcodes** for list of codes used in mrch\n"
				+ "**mtalk<words>\\mtk<words>** to talk through the bot\n" 
				+ "**mani** <anime name> to get an anime"
				+ "**mhug <@someone>** to give them a hug\n"
				+ "gifs\nEmotes\n" + "*dm @OtakuPug#4791 if you want anything added!*");
		channel.sendMessage(ebhelp.build()).queue();

	}

	private void talk(MessageReceivedEvent e, MessageChannel channel, Message msg) {
		String[] temp = msg.getContentRaw().split(" ");
		try {
			String msg3 = "";
			for (int i = 1; i < temp.length; i++) {
				msg3 += temp[i] + " ";
			}
			channel.sendMessage(msg3).queue();
			e.getMessage().delete().queue();
		} catch (ArrayIndexOutOfBoundsException e1) {
			channel.sendMessage("missing a message!").queue();
		} catch (IllegalArgumentException e2) {
			channel.sendMessage("missing a message!").queue();
		}

	}

	private void sendNhentai(MessageReceivedEvent e, MessageChannel channel, Message msg) throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter("codes.txt", true));

		String[] temp = msg.getContentRaw().split(" ");
		String cmd = temp[0];
		try {
			String code = temp[1];
			pw.println(code);
			pw.flush();
			pw.close();
			channel.sendMessage("https://nhentai.net/g/" + code + "/").queue();
		} catch (ArrayIndexOutOfBoundsException e1) {
			channel.sendMessage("missing a code!").queue();
		}

	}

	public static void restart(MessageChannel channel) throws RuntimeException, IOException {
		channel.sendMessage("restarting").queue();

		String array[] = { "sudo", "shutdown -r now" };
		String pas = "password";
		Process proc = Runtime.getRuntime().exec(array);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
		writer.write("");
		System.exit(0);
	}
}
