/*****************************************************************************
 * ------------------------------------------------------------------------- *
 * Licensed under the Apache License, Version 2.0 (the "License");           *
 * you may not use this file except in compliance with the License.          *
 * You may obtain a copy of the License at                                   *
 *                                                                           *
 * http://www.apache.org/licenses/LICENSE-2.0                                *
 *                                                                           *
 * Unless required by applicable law or agreed to in writing, software       *
 * distributed under the License is distributed on an "AS IS" BASIS,         *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
 * See the License for the specific language governing permissions and       *
 * limitations under the License.                                            *
 *****************************************************************************/
package org.mu.util;

import static java.util.Objects.requireNonNull;

import java.util.AbstractList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/** Some relatively trivial re-invented wheels as cost of 0-dependency. */
final class Utils {
  /** Only need it because we don't have Guava Lists.transform(). */
  static <F, T> List<T> mapList(List<? extends F> list, Function<? super F, ? extends T> mapper) {
    requireNonNull(list);
    requireNonNull(mapper);
    return new AbstractList<T>() {
      @Override public int size() {
        return list.size();
      }
      @Override public T get(int index) {
        return mapper.apply(list.get(index));
      }
    };
  }

  static <T> Predicate<Object> typed(Class<T> type, Predicate<? super T> condition) {
    requireNonNull(type);
    requireNonNull(condition);
    return x -> type.isInstance(x) && condition.test(type.cast(x));
  }

  /**
   * Casts {@code object} to {@code type} if it's a non-null instance of {@code T}, or else
   * returns {@code Optional.empty()}.
   */
  static <T> Optional<T> cast(Object object, Class<T> type) {
    return type.isInstance(object) ? Optional.of(type.cast(object)) : Optional.empty();
  }

  /** Propagates {@code exception} if it's unchecked, or else return it as is. */
  static <E extends Throwable> E propagateIfUnchecked(E e) {
    if (e instanceof RuntimeException) {
      throw (RuntimeException) e;
    } else if (e instanceof Error) {
      throw (Error) e;
    } else {
      return e;
    }
  }
}
