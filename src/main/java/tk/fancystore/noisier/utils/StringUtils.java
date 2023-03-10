package tk.fancystore.noisier.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe com utilitários relacionado a {@link String}.
 */
public class StringUtils {

  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###");
  private static final Pattern COLOR_PATTERN = Pattern.compile("(?i)(§)[0-9A-FK-OR]");
  private static final String[] SHORT_SUFFIX = new String[]{"", "k", "m", "b", "t"};

  /**
   * Formata um número
   *
   * @param number Para formatar.
   * @return O número formatado.
   */
  public static String formatShortNumber(int number) {
    if (number > 999) {
      String[] split = DECIMAL_FORMAT.format(number).split("\\.");
      String n = split[1].substring(0, 1);
      return split[0] + (n.equals("0") ? "" : "," + n) + SHORT_SUFFIX[split.length - 1];
    }
    return String.valueOf(number);
  }

  /**
   * Formata um número "#,###" através do {@link DecimalFormat}
   *
   * @param number Para formatar.
   * @return O número formatado.
   */
  public static String formatNumber(int number) {
    return DECIMAL_FORMAT.format(number);
  }

  /**
   * Formata um número "#,###" através do {@link DecimalFormat}
   *
   * @param number Para formatar.
   * @return O número formatado.
   */
  public static String formatNumber(long number) {
    return DECIMAL_FORMAT.format(number);
  }

  /**
   * Formata um número "#,###" através do {@link DecimalFormat}
   *
   * @param number Para formatar.
   * @return O número formatado.
   */
  public static String formatNumber(double number) {
    return DECIMAL_FORMAT.format(number);
  }

  /**
   * Remove todas as cores de uma String.<br/>
   * Color code: §
   *
   * @param input A string para remover as cores.
   * @return A string sem cores.
   */
  public static String stripColors(final String input) {
    if (input == null) {
      return null;
    }

    return COLOR_PATTERN.matcher(input).replaceAll("");
  }

  /**
   * Formata os {@code &} para o color code {@code §}.
   *
   * @param textToFormat A string para formatar as cores.
   * @return A string com as cores formatadas.
   */
  public static String formatColors(String textToFormat) {
    return translateAlternateColorCodes('&', textToFormat);
  }

  /**
   * Desformata o color code {@code §} para {@code &}.
   *
   * @param textToDeFormat A string para desformatar as cores.
   * @return A string com as cores desformatadas.
   */
  public static String deformatColors(String textToDeFormat) {
    Matcher matcher = COLOR_PATTERN.matcher(textToDeFormat);
    while (matcher.find()) {
      String color = matcher.group();
      textToDeFormat = textToDeFormat.replaceFirst(Pattern.quote(color), Matcher.quoteReplacement("&" + color.substring(1)));
    }

    return textToDeFormat;
  }

  /**
   * Formata os {altColorChar} para o color code {@code §}.
   *
   * @param altColorChar    O caractere que é definido como color code.
   * @param textToTranslate A string para formatar as cores.
   * @return A string com as cores formatadas.
   */
  public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
    Pattern pattern = Pattern.compile("(?i)(" + altColorChar + ")[0-9A-FK-OR]");

    Matcher matcher = pattern.matcher(textToTranslate);
    while (matcher.find()) {
      String color = matcher.group();
      textToTranslate = textToTranslate.replaceFirst(Pattern.quote(color), Matcher.quoteReplacement("§" + color.substring(1)));
    }

    return textToTranslate;
  }

  /**
   * Pega a primeira cor de uma {@code String}.
   *
   * @param input A string para pegar a cor.
   * @return A primeira cor ou {@code ""(vazio)} caso não encontre nenhuma.
   */
  public static String getFirstColor(String input) {
    Matcher matcher = COLOR_PATTERN.matcher(input);
    String first = "";
    if (matcher.find()) {
      first = matcher.group();
    }

    return first;
  }

  /**
   * Pega a última cor de uma {@code String}.
   *
   * @param input A string para pegar a cor.
   * @return A última cor ou {@code ""(vazio)} caso não encontre nenhuma.
   */
  public static String getLastColor(String input) {
    Matcher matcher = COLOR_PATTERN.matcher(input);
    String last = "";
    while (matcher.find()) {
      last = matcher.group();
    }

    return last;
  }

  /**
   * Pega a última cor de uma {@code String}.
   *
   * @param input A string para pegar a cor.
   * @return Todas as cores ou {@code ""(vazio)} caso não encontre nenhuma.
   */
  public static String getAllColors(String input) {
    Matcher matcher = COLOR_PATTERN.matcher(input);
    StringBuilder colors = new StringBuilder();
    while (matcher.find()) {
      colors.append(matcher.group());
    }

    return colors.toString();
  }

  /**
   * Repete uma String várias vezes.
   *
   * @param repeat A string para repetir.
   * @param amount A quantidade de vezes que será repetida.
   * @return Resultado da repetição.
   */
  public static String repeat(String repeat, int amount) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < amount; i++) {
      sb.append(repeat);
    }

    return sb.toString();
  }

  /**
   * Junta uma Array em uma {@code String} utilizando um separador.
   *
   * @param array     A array para juntar.
   * @param index     O ínicio da iteração da array (0 = toda a array).
   * @param separator O separador da junção.
   * @return Resultado da junção.
   */
  public static <T> String join(T[] array, int index, String separator) {
    StringBuilder joined = new StringBuilder();
    for (int slot = index; slot < array.length; slot++) {
      joined.append(array[slot].toString()).append(slot + 1 == array.length ? "" : separator);
    }

    return joined.toString();
  }

  /**
   * Junta uma Array em uma {@code String} utilizando um separador.
   *
   * @param array     A array para juntar.
   * @param separator O separador da junção.
   * @return Resultado da junção.
   */
  public static <T> String join(T[] array, String separator) {
    return join(array, 0, separator);
  }

  /**
   * Junta uma Coleção em uma {@code String} utilizando um separador.
   *
   * @param collection A coleção para juntar.
   * @param separator  O separador da junção.
   * @return Resultado da junção.
   */
  public static <T> String join(Collection<T> collection, String separator) {
    return join(collection.toArray(new Object[0]), separator);
  }

  /**
   * Quebra uma {@code String} várias vezes para criar linhas com o tamanho máximo definido.<br/>
   * <b>Observação:</b> Esse método é uma variação do {@link StringUtils#split(String, int, boolean)}
   * com o parâmetro {@code ignoreCompleteWords} definido como {@code false}.
   *
   * @param toSplit String para quebrar.
   * @param length  Tamanho máximo das linhas.
   * @return Resultado da separação.
   */
  public static String[] split(String toSplit, int length) {
    return split(toSplit, length, false);
  }

  /**
   * "Capitaliza" uma String Exemplo: MAXTER se torna Maxter
   *
   * @param toCapitalise String para capitalizar
   * @return Resultado capitalizado.
   */
  public static String capitalise(String toCapitalise) {
    StringBuilder result = new StringBuilder();

    String[] splittedString = toCapitalise.split(" ");
    for (int index = 0; index < splittedString.length; index++) {
      String append = splittedString[index];
      result.append(append.substring(0, 1).toUpperCase()).append(append.substring(1).toLowerCase()).append(index + 1 == splittedString.length ? "" : " ");
    }

    return result.toString();
  }

  /**
   * Quebra uma {@code String} várias vezes para criar linhas com o tamanho máximo definido.
   *
   * @param toSplit               String para quebrar.
   * @param length                Tamanho máximo das linhas.
   * @param ignoreIncompleteWords Se irá ignorar a quebra de palavras ou não (caso esteja desativado e
   *                              for quebrar uma palavra, ela irá ser removida da linha atual e adicionar na próxima).
   * @return Resultado da separação.
   */
  public static String[] split(String toSplit, int length, boolean ignoreIncompleteWords) {
    StringBuilder result = new StringBuilder(), current = new StringBuilder();

    char[] arr = toSplit.toCharArray();
    for (int charId = 0; charId < arr.length; charId++) {
      char character = arr[charId];
      if (current.length() == length) {
        if (!ignoreIncompleteWords) {
          List<Character> removedChars = new ArrayList<>();
          for (int l = current.length() - 1; l > 0; l--) {
            if (current.charAt(l) == ' ') {
              current.deleteCharAt(l);
              result.append(current).append("\n");
              Collections.reverse(removedChars);
              current = new StringBuilder(join(removedChars, ""));
              break;
            }

            removedChars.add(current.charAt(l));
            current.deleteCharAt(l);
          }

          removedChars.clear();
        } else {
          result.append(current).append("\n");
          current = new StringBuilder();
        }
      }

      current.append(current.length() == 0 && character == ' ' ? "" : character);
      if (charId + 1 == arr.length) {
        result.append(current).append("\n");
      }
    }

    return result.toString().split("\n");
  }

  /**
   * Cria um {@link TextComponent} a partir de uma {@code String}.<br/>
   * <br/>
   * Referências:<br/>
   * -> Hover: {@link HoverEvent}<br/>
   * -> Click: {@link ClickEvent}<br/>
   * <br/>
   * Funções:<br/>
   * -> '{@code </append>}' = Anexa um novo component<br/>
   * -> '{@code </actions>}' = Adicionar actions a um component<br/>
   * -> '{@code </and>}' = Adicionar uma outra action<br/>
   * <br/>
   * Actions:<br/>
   * -> 'hover:show_text>' = texto ao passar o mouse<br/>
   * -> 'click:run_command>' = fazer o Player executar comando<br/>
   * -> 'click:suggest_command>' = sugerir um comando no chat<br/>
   * -> 'click:open_url>' = abrir um link<br/>
   * -> 'click:change_page>' = mudar página do livro<br/>
   * <br/>
   * Formato: "{@code </append>}TEXTO{@code </actions>}ACTIONS"<br/>
   * Exemplo: "Open {@code </append>}link{@code </actions>}hover:show_text>click to open{@code </and>}click:open_url>https://example.net"<br/>
   * <br/>
   *
   * @param toParse String para formatar.<br/>
   * @return O {@link TextComponent} criado.<br/>
   */
  public static TextComponent toTextComponent(String toParse) {
    TextComponent result = new TextComponent("");

    for (String part : formatColors(toParse).split("</append>")) {
      String[] split = part.split("</actions>");
      TextComponent textComponent = new TextComponent(TextComponent.fromLegacyText(split[0]));
      if (split.length > 1) {
        for (String action : split[1].split("</and>")) {
          if (action.startsWith("hover:show_text>")) {
            textComponent.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(action.split("hover:show_text>")[1])
            ));
          } else if (action.startsWith("click:run_command>")) {
            textComponent.setClickEvent(new ClickEvent(
                ClickEvent.Action.RUN_COMMAND, action.split("click:run_command>")[1]
            ));
          } else if (action.startsWith("click:suggest_command>")) {
            textComponent.setClickEvent(new ClickEvent(
                ClickEvent.Action.SUGGEST_COMMAND, action.split("click:suggest_command>")[1]
            ));
          } else if (action.startsWith("click:open_url>")) {
            textComponent.setClickEvent(new ClickEvent(
                ClickEvent.Action.OPEN_URL, action.split("click:open_url>")[1]
            ));
          } else if (action.startsWith("click:change_page>")) {
            textComponent.setClickEvent(new ClickEvent(
                ClickEvent.Action.CHANGE_PAGE, action.split("click:change_page>")[1]
            ));
          }
        }
      }

      result.addExtra(textComponent);
    }

    return result;
  }
}
