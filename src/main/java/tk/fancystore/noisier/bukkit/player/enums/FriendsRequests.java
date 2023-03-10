package tk.fancystore.noisier.bukkit.player.enums;

public enum FriendsRequests {
  ATIVADO,
  DESATIVADO;

  public static FriendsRequests getByOrdinal(long ordinal) {
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

  public FriendsRequests next() {
    if (this == DESATIVADO) {
      return ATIVADO;
    }

    return DESATIVADO;
  }

  private static final FriendsRequests[] VALUES = values();
}