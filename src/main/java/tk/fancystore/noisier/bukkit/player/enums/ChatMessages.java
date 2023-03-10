package tk.fancystore.noisier.bukkit.player.enums;

public enum ChatMessages {
  ATIVADO,
  DESATIVADO;

  private static final ChatMessages[] VALUES = values();

  public static ChatMessages getByOrdinal(long ordinal) {
    if (ordinal < 2 && ordinal > -1) {
      return VALUES[(int) ordinal];
    }

    return null;
  }

  public String getInkSack() {
    if (this == ATIVADO) {
      return "10";
    }

    return "8";
  }

  public String getName() {
    if (this == ATIVADO) {
      return "§aAtivado";
    }

    return "§cDesativado";
  }

  public ChatMessages next() {
    if (this == DESATIVADO) {
      return ATIVADO;
    }

    return DESATIVADO;
  }
}