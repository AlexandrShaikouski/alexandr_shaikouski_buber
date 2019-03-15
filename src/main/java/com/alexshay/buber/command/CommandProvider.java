package com.alexshay.buber.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Command Provider
 */
public class CommandProvider {
    private static CommandProvider instance = new CommandProvider();
    private Map<String, Command> commandMap = new HashMap<>();

    public static CommandProvider getInstance() {
        return instance;
    }

    private CommandProvider() {
        commandMap.put("sign_in", new CommandSignInUser());
        commandMap.put("list_clients", new CommandShowAllClient());
        commandMap.put("list_drivers", new CommandShowAllDriver());
        commandMap.put("list_admin", new CommandShowAllAdmin());
        commandMap.put("create_user", new CommandCreateUser());
        commandMap.put("create_page", new CommandShowCreatePage());
        commandMap.put("success_page", new CommandSuccessPage());
        commandMap.put("delete_user", new CommandDeleteUser());
        commandMap.put("info_user", new CommandInfoUser());
        commandMap.put("update_user", new CommandUpdateUser());
        commandMap.put("delete_ban", new CommandDeleteBan());
        commandMap.put("delete_bonus", new CommandDeleteBonus());
        commandMap.put("list_orders", new CommandShowAllOrder());
        commandMap.put("register_page", new CommandShowRegisterPage());
        commandMap.put("reset_password", new CommandResetPassword());
        commandMap.put("sign_out", new CommandLogOut());
        commandMap.put("main_page", new CommandShowMainPage());
        commandMap.put("locale", new CommandChangeLocale());
        commandMap.put("order", new CommandCreateOrder());
        commandMap.put("check_order_driver", new CommandCheckOrderDriver());
        commandMap.put("check_order_client", new CommandCheckOrderClient());
        commandMap.put("accept_order", new CommandAcceptOrder());
    }

    /**
     * Return command by name
     * @param command name
     * @return command implementation
     */
    public Command takeCommand(String command) {
        return commandMap.get(command);
    }
}
