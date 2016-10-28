package net.frozenorb.potpvp.postmatchinv;

import net.frozenorb.potpvp.match.MatchTeam;
import net.frozenorb.qlib.util.UUIDUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class PostMatchInvLang {

    private static final TextComponent TEAM_1_HEADER_COMPONENT = new TextComponent("Team 1: ");
    private static final TextComponent TEAM_2_HEADER_COMPONENT = new TextComponent("Team 2: ");
    private static final TextComponent YOUR_TEAM_COMPONENT = new TextComponent("Your team: ");
    private static final TextComponent ENEMY_TEAM_COMPONENT = new TextComponent("Enemy team: ");
    private static final TextComponent LINE_COMPONENT = new TextComponent("-----------------------------------------------------");
    private static final TextComponent INVENTORY_HEADER_COMPONENT = new TextComponent("Post-Match Inventories ");
    private static final TextComponent COMMA_COMPONENT = new TextComponent(", ");

    static {
        TEAM_1_HEADER_COMPONENT.setColor(ChatColor.LIGHT_PURPLE);
        TEAM_2_HEADER_COMPONENT.setColor(ChatColor.AQUA);
        YOUR_TEAM_COMPONENT.setColor(ChatColor.GREEN);
        ENEMY_TEAM_COMPONENT.setColor(ChatColor.RED);
        LINE_COMPONENT.setColor(ChatColor.GRAY);
        LINE_COMPONENT.setStrikethrough(true);
        INVENTORY_HEADER_COMPONENT.setColor(ChatColor.GOLD);
        COMMA_COMPONENT.setColor(ChatColor.YELLOW);

        TextComponent clickToView = new TextComponent("(click name to view)");
        clickToView.setColor(ChatColor.GRAY);
        INVENTORY_HEADER_COMPONENT.addExtra(clickToView);
    }

    public static TextComponent[][] spectatorMessages(TextComponent[] team1Buttons, TextComponent[] team2Buttons) {
        return new TextComponent[][] {
            { LINE_COMPONENT },
            { INVENTORY_HEADER_COMPONENT },
            { TEAM_1_HEADER_COMPONENT },
            team1Buttons,
            { TEAM_2_HEADER_COMPONENT },
            team2Buttons,
            { LINE_COMPONENT }
        };
    }

    public static TextComponent[][] teamMessages(TextComponent[] yourTeam, TextComponent[] enemyTeam) {
        return new TextComponent[][] {
            { LINE_COMPONENT },
            { INVENTORY_HEADER_COMPONENT },
            { YOUR_TEAM_COMPONENT },
            yourTeam,
            { ENEMY_TEAM_COMPONENT },
            enemyTeam,
            { LINE_COMPONENT }
        };
    }

    public static TextComponent[] clickToViewLine(MatchTeam team) {
        List<TextComponent> components = new ArrayList<>();

        for (UUID member : team.getAllMembers()) {
            String memberName = UUIDUtils.name(member);
            TextComponent component = new TextComponent();

            component.setText(memberName);
            component.setColor(ChatColor.YELLOW);
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Click to view inventory of " + ChatColor.GOLD + memberName).create()));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/checkPostMatchData " + memberName));

            components.add(component);
            components.add(COMMA_COMPONENT);
        }

        components.remove(components.size() - 1); // remove trailing comma
        return components.toArray(new TextComponent[components.size()]);
    }


}