package chapter_2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

@DisplayName("Calculator 클래스 테스트의")
class StringCalculatorTest {

    StringCalculator stringCalculator;

    @BeforeEach
    void setUp() {
        stringCalculator = new StringCalculator();
    }

    private static class PositiveNumberGenerator implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.generate(() -> new Random().nextInt(Integer.MAX_VALUE) + 1)
                .limit(100)
                .map(String::valueOf)
                .map(Arguments::of);
        }
    }

    @Nested
    @DisplayName("add 메소드는")
    class DescribeAddMethod {

        @Nested
        @DisplayName("null을 입력하면")
        class ContextCallWithNull {

            @Test
            @DisplayName("0을 반환한다.")
            void it_return_0() {
                assertThat(stringCalculator.add(null)).isZero();
            }
        }

        @Nested
        @DisplayName("\"\"를 입력하면")
        class ContextCallWithEmptyString {

            @Test
            @DisplayName("0을 반환한다.")
            void it_return_0() {
                assertThat(stringCalculator.add("")).isZero();
            }
        }

        @Nested
        @DisplayName("숫자 하나를 입력하면")
        class ContextCallWithNumberOneEach {

            @Test
            @DisplayName("해당 숫자를 그대로 반환한다.")
            void it_return_input() {
                assertThat(stringCalculator.add("6")).isEqualTo(6);
            }
        }

        @Nested
        @DisplayName("쉼표(,)를 구분자로 숫자를 두개 입력하면")
        class ContextCallWithTwoNumberWithComma {

            @Test
            @DisplayName("두 수의 합을 반환한다.")
            void it_return_sum() {
                assertThat(stringCalculator.add("3,5")).isEqualTo(8);
            }
        }

        @Nested
        @DisplayName("콜론(:)를 구분자로 숫자를 두개 입력하면")
        class ContextCallWithTwoNumberWithColon {

            @Test
            @DisplayName("두 수의 합을 반환한다.")
            void it_return_sum() {
                assertThat(stringCalculator.add("3:5")).isEqualTo(8);
            }
        }

        @Nested
        @DisplayName("커스텀구분자와 숫자를 두개 입력하면")
        class ContextCallWithTwoNumberWithCustomDelimeter {

            @Test
            @DisplayName("두 수의 합을 반환한다.")
            void it_return_sum() {
                assertThat(stringCalculator.add("//;\n3;5")).isEqualTo(8);
            }
        }

        @Nested
        @DisplayName("음수를 입력하면")
        class ContextCallWithNegativeNumber {

            @Test
            @DisplayName("RuntimeException 을 던진다.")
            void itThrowRuntimeException() {
                assertThatThrownBy(() -> stringCalculator.add("-1"))
                    .isExactlyInstanceOf(RuntimeException.class);
            }
        }
    }
}
