package se.johannesdahlgren.adventofcode2019;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;

import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.johannesdahlgren.adventofcode2019.matchers.AdjacentDigitsMatcher;
import se.johannesdahlgren.adventofcode2019.matchers.NeverDecreasesMatcher;

class Day4Test {

  private Day4 day4;
  private int minValue = 248345;
  private int maxValue = 746315;

  @BeforeEach
  void setUp() {
    day4 = new Day4(minValue, maxValue);
  }

  @Test
  void atLeastOnePassword() {
    List<Integer> passwords = day4.getPasswords();
    assertThat(passwords, is(not(empty())));
  }

  @Test
  void exactly6Digits() {
    List<Integer> passwords = day4.getPasswords();
    for (Integer password : passwords) {
      assertThat(String.valueOf(password).length(), is(6));
    }
  }

  @Test
  void noPasswordsWhenRangeStartsLongerThan6() {
    List<Integer> passwords = new Day4(1111111, 1111113).getPasswords();
    assertThat(passwords, is(empty()));
  }

  @Test
  void noPasswordsWhenRangeEndsShorterThan6() {
    List<Integer> passwords = new Day4(111, 113).getPasswords();
    assertThat(passwords, is(empty()));
  }

  @Test
  void withinRange() {
    List<Integer> passwords = day4.getPasswords();
    for (Integer password : passwords) {
      assertThat(password, greaterThan(minValue));
      assertThat(password, lessThan(maxValue));
    }
  }

  @Test
  void atLeastTwoAdjacentDigits() {
    List<Integer> passwords = day4.getPasswords();
    for (Integer password : passwords) {
      MatcherAssert.assertThat(password, AdjacentDigitsMatcher.containsAtLeastTwoAdjacentDigits());
    }
  }

  @Test
  void neverDecreases() {
    List<Integer> passwords = day4.getPasswords();
    for (Integer password : passwords) {
      MatcherAssert.assertThat(password, NeverDecreasesMatcher.digitsNeverDecreases());
    }
  }

  @Test
  void howManyValidPasswords() {
    List<Integer> passwords = day4.getPasswords();
    assertThat(passwords.size(), is(1019));
  }

  @Test
  void example1() {
    int password = 111111;
    MatcherAssert.assertThat(password, AdjacentDigitsMatcher.containsAtLeastTwoAdjacentDigits());
    MatcherAssert.assertThat(password, NeverDecreasesMatcher.digitsNeverDecreases());
  }

  @Test
  void example2() {
    int password = 223450;
    MatcherAssert.assertThat(password, AdjacentDigitsMatcher.containsAtLeastTwoAdjacentDigits());
    MatcherAssert.assertThat(password, Matchers.is(Matchers.not(NeverDecreasesMatcher.digitsNeverDecreases())));
  }

  @Test
  void example3() {
    int password = 123789;
    MatcherAssert
        .assertThat(password, Matchers.is(Matchers.not(AdjacentDigitsMatcher.containsAtLeastTwoAdjacentDigits())));
    MatcherAssert.assertThat(password, NeverDecreasesMatcher.digitsNeverDecreases());
  }

}