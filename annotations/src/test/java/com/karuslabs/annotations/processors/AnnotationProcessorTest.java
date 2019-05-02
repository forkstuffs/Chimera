/*
 * The MIT License
 *
 * Copyright 2019 Matthias.
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
package com.karuslabs.annotations.processors;

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.*;
import org.mockito.quality.Strictness;

import static javax.tools.Diagnostic.Kind.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AnnotationProcessorTest {
    
    AnnotationProcessor processor = spy(new AnnotationProcessor() {});
    Messager messager = mock(Messager.class);
    Elements elements = mock(Elements.class);
    Types types = mock(Types.class);
    ProcessingEnvironment environment = when(mock(ProcessingEnvironment.class).getMessager()).thenReturn(messager).getMock();
    
    
    
    @Test
    void process() {
        when(environment.getElementUtils()).thenReturn(elements);
        when(environment.getTypeUtils()).thenReturn(types);
        
        processor.init(environment);
        
        var element = mock(TypeElement.class);
        RoundEnvironment environment = when(mock(RoundEnvironment.class).getElementsAnnotatedWithAny(any(Set.class))).thenReturn(Set.of(element)).getMock();
        
        assertFalse(processor.process(Set.of(), environment));
    }
    
    
    @Test
    void messsage() {
        var element = mock(Element.class);
        processor.init(environment);
        
        processor.error(element, "Error");
        verify(messager).printMessage(ERROR, "Error", element);
        
        processor.warn(element, "Warning");
        verify(messager).printMessage(WARNING, "Warning", element);
        
        processor.note(element, "Note");
        verify(messager).printMessage(NOTE, "Note", element);
    }

} 