/*
 * The MIT License
 *
 * Copyright 2017 Karus Labs.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.karuslabs.commons.command.parser;

import java.util.*;

import junitparams.*;

import org.bukkit.configuration.ConfigurationSection;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(JUnitParamsRunner.class)
public class ElementTest {
    
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    private Element<String> element;
    private Map<String, String> definitions;
    
    
    public ElementTest() {
        element = spy(new Element<String>(definitions = spy(new HashMap<>())) {});
    }
    
    
    @Before
    public void setup() {
        definitions.clear();
    }
    
    
    @Test
    public void define() {
        doReturn("value").when(element).parse("value");
        
        element.define("key", "value");
        
        assertFalse(element.getDefinitions().isEmpty());
        verify(element).parse("value");
    }
    
    
    @Test
    public void parse() {
        element.getDefinitions().put("key", "value");
        
        assertEquals("value", element.parse("key"));
    }
    
    
    @Test
    @Parameters
    public void parse_ThrowsException(Object value) {
        exception.expect(IllegalArgumentException.class);
        
        element.parse(value);
    }
    
    protected Object[] parametersForParse_ThrowsException() {
        ConfigurationSection config = mock(ConfigurationSection.class);
        
        return new Object[] {
            new Object(), config
        };
    }
    
}
