/*
 * The MIT License
 *
 * Copyright 2018 Karus Labs.
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
package com.karuslabs.commons.annotation.signature.signatures;

import com.karuslabs.commons.annotation.signature.*;

import javax.lang.model.element.*;


public abstract class TypeSignature<T extends Element> implements Signature<T> {
    
    protected Modifiers modifiers;
    protected Type type;
    protected TypeParameter generics;
    protected Expression expression;
    
    
    public TypeSignature(Modifiers modifiers, Type type, TypeParameter generics, Expression expression) {
        this.modifiers = modifiers;
        this.type = type;
        this.generics = generics;
        this.expression = expression;
    }
    
    
    protected boolean match(T element) {
        return modifiers.matches(element.getModifiers()) && expression.matches(element.getSimpleName());
    }
    
}
