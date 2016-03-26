package com.Ben12345rocks.VotingPlugin.Commands.Executers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Ben12345rocks.VotingPlugin.Main;
import com.Ben12345rocks.VotingPlugin.Utils;
import com.Ben12345rocks.VotingPlugin.Bungee.BungeeVote;
import com.Ben12345rocks.VotingPlugin.Commands.Commands;
import com.Ben12345rocks.VotingPlugin.Config.Config;
import com.Ben12345rocks.VotingPlugin.Config.ConfigBonusReward;
import com.Ben12345rocks.VotingPlugin.Config.ConfigFormat;
import com.Ben12345rocks.VotingPlugin.Config.ConfigVoteSites;
import com.Ben12345rocks.VotingPlugin.Events.VotiferEvent;
import com.Ben12345rocks.VotingPlugin.Messages.Messages;
import com.Ben12345rocks.VotingPlugin.Objects.User;
import com.Ben12345rocks.VotingPlugin.Objects.VoteSite;
import com.Ben12345rocks.VotingPlugin.UserData.Data;

public class CommandAdminVote implements CommandExecutor {

	ConfigBonusReward bonusReward = ConfigBonusReward.getInstance();

	Config config = Config.getInstance();

	ConfigFormat format = ConfigFormat.getInstance();

	private Main plugin;

	ConfigVoteSites voteSites = ConfigVoteSites.getInstance();

	public CommandAdminVote(Main plugin) {
		this.plugin = plugin;
	}

	public void addBonusRewardCommandConsole(CommandSender sender, String cmd) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.BonusReward.AddCommandConsole")) {
			List<String> cmds = ConfigBonusReward.getInstance()
					.getConsoleCommands();
			cmds.add(cmd);
			ConfigBonusReward.getInstance().setConsoleCommands(cmds);
			sender.sendMessage(Utils.getInstance().colorize(
					"&cAdded console command &c&l" + cmd));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void addBonusRewardCommandPlayer(CommandSender sender, String cmd) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.BonusReward.AddCommandPlayer")) {
			List<String> cmds = ConfigBonusReward.getInstance()
					.getPlayerCommands();
			cmds.add(cmd);
			ConfigBonusReward.getInstance().setPlayerCommands(cmds);
			sender.sendMessage(Utils.getInstance().colorize(
					"&cAdded player command &c&l" + cmd));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void addBonusRewardItem(CommandSender sender, String item) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.BonusReward.AddItem")) {
			if (Utils.getInstance().isPlayer(sender)) {
				Player player = (Player) sender;
				if (player.getInventory().getItemInMainHand() != null) {

					sender.sendMessage(Utils.getInstance().colorize(
							"&cTrying to add item..."));
					Bukkit.getScheduler().runTaskAsynchronously(plugin,
							new Runnable() {

								@Override
								public void run() {
									ConfigBonusReward.getInstance().addItem(

											item,
											player.getInventory()
													.getItemInMainHand());
									sender.sendMessage(Utils.getInstance()
											.colorize(
													"&cAdded item &c&l" + item));

								}
							});

				} else {
					sender.sendMessage(Utils.getInstance().colorize(
							"&cHold an item"));
				}
			} else {
				sender.sendMessage(Messages.getInstance().mustBePlayer());
			}
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void addVoteSiteCommandConsole(CommandSender sender,
			String voteSite, String cmd) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.VoteSite.AddCommandConsole")) {
			List<String> cmds = ConfigVoteSites.getInstance()
					.getConsoleCommands(voteSite);
			cmds.add(cmd);
			ConfigVoteSites.getInstance().setConsoleCommands(voteSite, cmds);
			sender.sendMessage(Utils.getInstance().colorize(
					"&cAdded console command &c&l" + cmd + "&c on &c&l"
							+ voteSite));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void addVoteSiteCommandPlayer(CommandSender sender, String voteSite,
			String cmd) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.VoteSite.AddCommandPlayer")) {
			List<String> cmds = ConfigVoteSites.getInstance()
					.getPlayerCommands(voteSite);
			cmds.add(cmd);
			ConfigVoteSites.getInstance().setPlayerCommands(voteSite, cmds);
			sender.sendMessage(Utils.getInstance().colorize(
					"&cAdded player command &c&l" + cmd + "&c on &c&l"
							+ voteSite));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void addVoteSiteItem(CommandSender sender, String voteSite,
			String item) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.VoteSite.AddItem")) {
			if (Utils.getInstance().isPlayer(sender)) {
				Player player = (Player) sender;
				if (player.getInventory().getItemInMainHand() != null) {

					sender.sendMessage(Utils.getInstance().colorize(
							"&cTrying to add item..."));
					Bukkit.getScheduler().runTaskAsynchronously(plugin,
							new Runnable() {

								@Override
								public void run() {
									ConfigVoteSites.getInstance().addItem(
											voteSite,
											item,
											player.getInventory()
													.getItemInMainHand());
									sender.sendMessage(Utils.getInstance()
											.colorize(
													"&cAdded item &c&l" + item
															+ " &cto "
															+ voteSite));

								}
							});

				} else {
					sender.sendMessage(Utils.getInstance().colorize(
							"&cHold an item"));
				}
			} else {
				sender.sendMessage(Messages.getInstance().mustBePlayer());
			}
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void bungeeVote(CommandSender sender, String voteSite,
			String playerName) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.BungeeVote")) {
			BungeeVote.getInstance().sendBungeeVote(voteSite, playerName);
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void createVoteSite(CommandSender sender, String voteSite) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.VoteSite.Create")) {
			sender.sendMessage(Utils.getInstance().colorize(
					"&cCreating VoteSite..."));
			Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {

				@Override
				public void run() {
					ConfigVoteSites.getInstance().generateVoteSite(voteSite);
					sender.sendMessage(Utils.getInstance().colorize(
							"&cCreated VoteSite: &c&l" + voteSite));
				}
			});
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void globalVote(CommandSender sender, String voteSite,
			String playerName) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.GlobalVote")) {
			VotiferEvent.playerVote(voteSite, playerName);
			BungeeVote.getInstance().sendBungeeVote(voteSite, playerName);
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void help(CommandSender sender) {
		if (Utils.getInstance()
				.hasPermission(sender, "Commands.AdminVote.Help")) {
			if (!config.disableJson()) {
				Utils.getInstance().sendMessageComponent(sender,
						Commands.getInstance().adminVoteHelp());
			} else {
				sender.sendMessage(Commands.getInstance()
						.adminHelpTextColored());
			}
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (args.length == 0) {
			help(sender);
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("help")
					|| args[0].equalsIgnoreCase("?")) {
				help(sender);
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")) {
				reload(sender);
				return true;
			}
			if (args[0].equalsIgnoreCase("version")) {
				version(sender);
				return true;
			}

			if (args[0].equalsIgnoreCase("sites")) {
				sites(sender);
				return true;
			}

		}

		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("sites")) {
				site(sender, args[1]);
				return true;
			}
			if (args[0].equalsIgnoreCase("uuid")) {
				uuid(sender, args[1]);
				return true;
			}

			if (args[0].equalsIgnoreCase("reset")) {
				if (args[1].equalsIgnoreCase("top")) {
					resetTop(sender);
				}
				return true;
			}

		}

		if (args.length >= 3) {
			if (args.length == 3) {
				if (args[0].equalsIgnoreCase("vote")) {
					vote(sender, args[1], args[2]);
					return true;
				}
				if (args[0].equalsIgnoreCase("bungeevote")) {
					bungeeVote(sender, args[1], args[2]);
					return true;
				}
				if (args[0].equalsIgnoreCase("globalvote")) {
					globalVote(sender, args[1], args[2]);
					return true;
				}
				if (args[0].equalsIgnoreCase("VoteSite")) {
					if (args[2].equalsIgnoreCase("Create")) {
						createVoteSite(sender, args[1]);
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("BonusReward")) {
					if (args[1].equalsIgnoreCase("AddItem")) {
						addBonusRewardItem(sender, args[2]);
						return true;
					}
					if (args[1].equalsIgnoreCase("SetMoney")) {
						if (Utils.getInstance().isInt(args[2])) {
							setBonusRewardMoney(sender,
									Integer.parseInt(args[2]));
						} else {
							sender.sendMessage(Utils.getInstance().colorize(
									"&cError on " + args[2]
											+ ", number expected"));
						}
						return true;
					}
					if (args[1].equalsIgnoreCase("SetGiveBonusReward")) {
						setGiveBonusReward(sender,
								Boolean.parseBoolean(args[2]));
						return true;
					}

				}
				if (args[0].equalsIgnoreCase("Config")) {
					if (args[1].equalsIgnoreCase("SetDebug")) {
						setConfigDebug(sender, Boolean.parseBoolean(args[2]));
						return true;
					}
					if (args[1].equalsIgnoreCase("SetBroadcastVote")) {
						setConfigBroadcastVote(sender,
								Boolean.parseBoolean(args[2]));
						return true;
					}
					if (args[1].equalsIgnoreCase("SetUpdateReminder")) {
						setConfigUpdateReminder(sender,
								Boolean.parseBoolean(args[2]));
						return true;
					}
					if (args[1].equalsIgnoreCase("SetAllowUnjoined")) {
						setConfigAllowUnjoined(sender,
								Boolean.parseBoolean(args[2]));
						return true;
					}
					if (args[1].equalsIgnoreCase("SetDisableJson")) {
						setConfigDisableJson(sender,
								Boolean.parseBoolean(args[2]));
						return true;
					}
				}
			}
			if (args[0].equalsIgnoreCase("BonusReward")) {
				if (args[1].equalsIgnoreCase("AddCommandPlayer")) {
					addBonusRewardCommandPlayer(sender, Utils.getInstance()
							.makeString(2, args));
					return true;
				}
				if (args[1].equalsIgnoreCase("AddCommandConsole")) {
					addBonusRewardCommandConsole(sender, Utils.getInstance()
							.makeString(2, args));
					return true;
				}
			}
		}

		if (args.length >= 4) {
			if (args.length == 4) {
				if (args[0].equalsIgnoreCase("settotal")) {
					if (Utils.getInstance().isInt(args[3])) {
						setTotal(sender, args[1], args[2],
								Integer.parseInt(args[3]));
					} else {
						sender.sendMessage(Utils.getInstance().colorize(
								"&cError on " + args[3] + ", number expected"));
					}
					return true;
				}
				if (args[0].equalsIgnoreCase("VoteSite")) {
					if (args[2].equalsIgnoreCase("AddItem")) {
						addVoteSiteItem(sender, args[1], args[3]);
						return true;
					}
					if (args[2].equalsIgnoreCase("SetMoney")) {
						if (Utils.getInstance().isInt(args[3])) {
							setVoteSiteMoney(sender, args[1],
									Integer.parseInt(args[3]));
						} else {
							sender.sendMessage(Utils.getInstance().colorize(
									"&cError on " + args[3]
											+ ", number expected"));
						}
						return true;
					}
					if (args[2].equalsIgnoreCase("SetServiceSite")) {
						setVoteSiteServiceSite(sender, args[1], args[3]);
						return true;
					}
					if (args[2].equalsIgnoreCase("SetDisabled")) {
						setVoteSiteDsiabled(sender, args[1],
								Boolean.parseBoolean(args[3]));
						return true;
					}
					if (args[2].equalsIgnoreCase("SetVoteDelay")) {
						if (Utils.getInstance().isInt(args[3])) {
							setVoteSiteVoteDelay(sender, args[1],
									Integer.parseInt(args[3]));
						} else {
							sender.sendMessage(Utils.getInstance().colorize(
									"&cError on " + args[3]
											+ ", number expected"));
						}
						return true;
					}
				}

			}
			if (args[0].equalsIgnoreCase("VoteSite")) {
				if (args[2].equalsIgnoreCase("AddCommandPlayer")) {
					addVoteSiteCommandPlayer(sender, args[1], Utils
							.getInstance().makeString(3, args));
					return true;
				}
				if (args[2].equalsIgnoreCase("AddCommandConsole")) {
					addVoteSiteCommandConsole(sender, args[1], Utils
							.getInstance().makeString(3, args));
					return true;
				}
			}

		}

		// invalid command
		sender.sendMessage(ChatColor.RED
				+ "No valid arguments, see /adminvote help!");

		return true;
	}

	public void reload(CommandSender sender) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.Reload")) {
			config.reloadData();
			format.reloadData();
			plugin.loadVoteSites();
			bonusReward.reloadData();
			plugin.updateTopUpdater();
			plugin.setupFiles();
			sender.sendMessage(ChatColor.RED + plugin.getName() + " reloaded!");
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void resetTop(CommandSender sender) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.Reset.Top")) {
			sender.sendMessage(Utils.getInstance().colorize(
					"&cResseting top voter..."));
			Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {

				@Override
				public void run() {
					resetTopVoter();
					sender.sendMessage(Utils.getInstance().colorize(
							"&cDone resseting top voter"));
					plugin.updateTopUpdater();
				}
			});
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void resetTopVoter() {
		for (User user : Data.getInstance().getUsers()) {
			for (VoteSite voteSite : ConfigVoteSites.getInstance()
					.getVoteSites()) {
				user.setTotal(voteSite, 0);
			}
		}
	}

	public void setBonusRewardMoney(CommandSender sender, int money) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.BonusReward.SetMoney")) {
			ConfigBonusReward.getInstance().setMoney(money);
			sender.sendMessage(Utils.getInstance().colorize(
					"&cSet money to &c&l" + money));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void setGiveBonusReward(CommandSender sender, boolean value) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.BonusReward.SetGiveBonusReward")) {
			ConfigBonusReward.getInstance().setGiveBonusReward(value);
			sender.sendMessage(Utils.getInstance().colorize(
					"&cSet GiveBonusReward to &c&l" + value));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void setTotal(CommandSender sender, String playerName,
			String voteSite, int amount) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.Set.Total")) {
			Data.getInstance().setTotal(new User(playerName), voteSite, amount);
			sender.sendMessage(ChatColor.GREEN + playerName
					+ " total votes for " + voteSite + " has been set to "
					+ amount);
			plugin.updateTopUpdater();
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void setVoteSiteDsiabled(CommandSender sender, String voteSite,
			boolean disabled) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.VoteSite.SetDisabled")) {
			ConfigVoteSites.getInstance().setDisabled(voteSite, disabled);
			sender.sendMessage(Utils.getInstance().colorize(
					"&cSet Dsiabled to &c&l" + disabled + "&c on &c&l"
							+ voteSite));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void setVoteSiteMoney(CommandSender sender, String voteSite,
			int money) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.VoteSite.SetMoney")) {
			ConfigVoteSites.getInstance().setMoney(voteSite, money);
			sender.sendMessage(Utils.getInstance().colorize(
					"&cSet money to &c&l" + money + "&c on &c&l" + voteSite));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void setVoteSiteServiceSite(CommandSender sender, String voteSite,
			String serviceSite) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.VoteSite.SetServiceSite")) {
			ConfigVoteSites.getInstance().setServiceSite(voteSite, serviceSite);
			sender.sendMessage(Utils.getInstance().colorize(
					"&cSet ServiceSite to &c&l" + serviceSite + "&c on &c&l"
							+ voteSite));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void setVoteSiteVoteDelay(CommandSender sender, String voteSite,
			int delay) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.VoteSite.SetVoteDelay")) {
			ConfigVoteSites.getInstance().setVoteDelay(voteSite, delay);
			sender.sendMessage(Utils.getInstance()
					.colorize(
							"&cSet VoteDelay to &c&l" + delay + "&c on &c&l"
									+ voteSite));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void site(CommandSender sender, String site) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.Sites.Site")) {
			sender.sendMessage(Commands.getInstance().voteCommandSiteInfo(site));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void sites(CommandSender sender) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.Sites")) {
			sender.sendMessage(Commands.getInstance().voteCommandSites());
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void uuid(CommandSender sender, String playerName) {
		if (Utils.getInstance()
				.hasPermission(sender, "Commands.AdminVote.UUID")) {
			sender.sendMessage(ChatColor.GREEN + "UUID of player "
					+ ChatColor.DARK_GREEN + playerName + ChatColor.GREEN
					+ " is: " + Utils.getInstance().getUUID(playerName));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void version(CommandSender sender) {
		if (sender instanceof Player) {
			if (Utils.getInstance().hasPermission(sender,
					"Commands.AdminVote.Version")) {
				Player player = (Player) sender;
				player.performCommand("bukkit:version " + plugin.getName());
			} else {
				sender.sendMessage(Messages.getInstance().noPerms());
			}
		} else {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
					"bukkit:version " + plugin.getName());
		}
	}

	public void vote(CommandSender sender, String voteSite, String playerName) {
		if (Utils.getInstance()
				.hasPermission(sender, "Commands.AdminVote.Vote")) {
			VotiferEvent.playerVote(voteSite, playerName);
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void setConfigDebug(CommandSender sender, boolean value) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.Config.SetDebug")) {
			Config.getInstance().setDebugEnabled(value);
			sender.sendMessage(Utils.getInstance().colorize(
					"&cSet Debug to &c&l" + value));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void setConfigBroadcastVote(CommandSender sender, boolean value) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.Config.SetBroadcastVote")) {
			Config.getInstance().setDebugEnabled(value);
			sender.sendMessage(Utils.getInstance().colorize(
					"&cSet BroadcastVote to &c&l" + value));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void setConfigUpdateReminder(CommandSender sender, boolean value) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.Config.SetUpdateReminder")) {
			Config.getInstance().setUpdateReminder(value);
			sender.sendMessage(Utils.getInstance().colorize(
					"&cSet UpdateReminder to &c&l" + value));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void setConfigAllowUnjoined(CommandSender sender, boolean value) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.Config.SetAllowUnjoined")) {
			Config.getInstance().setAllowUnJoined(value);
			sender.sendMessage(Utils.getInstance().colorize(
					"&cSet AllowUnjoined to &c&l" + value));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

	public void setConfigDisableJson(CommandSender sender, boolean value) {
		if (Utils.getInstance().hasPermission(sender,
				"Commands.AdminVote.Config.SetDisablejson")) {
			Config.getInstance().setDisableJson(value);
			sender.sendMessage(Utils.getInstance().colorize(
					"&cSet DisableJson to &c&l" + value));
		} else {
			sender.sendMessage(Messages.getInstance().noPerms());
		}
	}

}
