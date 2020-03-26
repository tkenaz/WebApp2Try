package webapp.command;

import com.mysql.cj.jdbc.jmx.LoadBalanceConnectionGroupManager;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private static final Logger LOGGER = Logger.getLogger(CommandContainer.class);
    private static Map<String, Command> commandsMap = new TreeMap<String, Command>();

    static {
        //common commands
        commandsMap.put("login", new LoginCommand());
        commandsMap.put("logout", new LogoutCommand());
        commandsMap.put("viewSettings", new ViewSettingsCommand());
        commandsMap.put("noCommand", new NoCommand());

        //user commands
        commandsMap.put("listMenu", new ListMenuCommand());

        //admin commands
        commandsMap.put("listOrders", new ListOrdersCommand);

        LOGGER.debug("Commands container was successfully initialized");
        LOGGER.trace("Number of commansa ==> " + commandsMap.size());
    }

    /**
     * Returns command object with the given name.
     * @param commandName
     *              Name of the command.
     * @return Command object.
     */

    public static Command get(String commandName) {
        if (commandName == null || !commandsMap.containsKey(commandName)) {
            LOGGER.trace("Command not found, name ==> " + commandName);
            return commandsMap.get("noCommand");
        }
        return commandsMap.get(commandName);
    }
}
