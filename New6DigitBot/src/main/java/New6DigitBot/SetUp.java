/**
 * 
 */

package New6DigitBot;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDABuilder;
import javax.security.auth.login.LoginException;

/**
 * @author OtakuPug
 *
 */
public class SetUp extends ListenerAdapter{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws LoginException
    {
        new JDABuilder("NjM1OTY0NTU1Mzg5OTYwMjIz.XknQkg.WlvefA2Rt_DtOX1ZNH3VXyxBsrU")
            .addEventListeners(new bot())            
            .setActivity(Activity.playing("mhelp"))
            
            .build();
    }

}
