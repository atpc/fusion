/*
 * Copyright (c) 2019  Thomas Orlando, ATPC
 *
 * This file is part of Fusion.
 *
 * Fusion is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Fusion is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Fusion.  If not, see <https://www.gnu.org/licenses/>.
 */

package one.atpc.fusion.ui;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public abstract class Keys extends KeyEvent {

    @SuppressWarnings("ConstantConditions")
    private Keys() {
        // Make sure the constructor fails
        super(null, 0, 0, 0, 0, (char) 0);
        throw new UnsupportedOperationException();
    }

    /**
     * Associates VK_XXX (as a String) with code (as Integer). This is
     * done to avoid the overhead of the reflective call to find the
     * constant.
     */
    private static VKCollection vks;

    @NotNull
    static String getVKText(int keyCode) {
        final VKCollection vkCollect = getVKCollection();
        final Integer key = keyCode;
        String name = vkCollect.findName(key);
        if (name != null) {
            return name.substring(3);
        }
        int expected_modifiers =
                (java.lang.reflect.Modifier.PUBLIC | java.lang.reflect.Modifier.STATIC | Modifier.FINAL);

        Field[] fields = KeyEvent.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.getModifiers() == expected_modifiers
                        && field.getType() == Integer.TYPE
                        && field.getName().startsWith("VK_")
                        && field.getInt(KeyEvent.class) == keyCode) {
                    name = field.getName();
                    vkCollect.put(name, key);
                    return name.substring(3);
                }
            } catch (IllegalAccessException e) {
                assert (false);
            }
        }
        return "UNKNOWN";
    }

    private static VKCollection getVKCollection() {
        if (vks == null) {
            vks = new VKCollection();
        }
        return vks;
    }

}

/*internal*/ final class VKCollection {
    private Map<Integer, String> code2name;
    private Map<String, Integer> name2code;

    VKCollection() {
        code2name = new HashMap<>();
        name2code = new HashMap<>();
    }

    synchronized void put(String name, Integer code) {
        assert (name != null) && (code != null);
        assert findName(code) == null;
        assert findCode(name) == null;
        code2name.put(code, name);
        name2code.put(name, code);
    }

    @Contract("null -> fail")
    synchronized Integer findCode(String name) {
        assert name != null;
        return name2code.get(name);
    }

    @Contract("null -> fail")
    synchronized String findName(Integer code) {
        assert code != null;
        return code2name.get(code);
    }
}