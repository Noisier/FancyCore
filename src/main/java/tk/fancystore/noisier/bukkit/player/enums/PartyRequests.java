package tk.fancystore.noisier.bukkit.player.enums;

public enum PartyRequests {
  ATIVADO,
  DESATIVADO;

  public static PartyRequests getByOrdinal(long ordinal) {
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

  public PartyRequests next() {
    if (this == DESATIVADO) {
      return ATIVADO;
    }

    return DESATIVADO;
  }

  private static final PartyRequests[] VALUES = values();
}