/*
 * This file is part of TestExpert.
 *
 * Copyright (C) 2012
 * "David Gageot" <david@gageot.net>,
 *
 * TestExpert is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TestExpert is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TestExpert. If not, see <http://www.gnu.org/licenses/>.
 */
package net.gageot.test.rules;

import com.google.common.collect.*;
import org.junit.rules.*;

import java.lang.Thread.*;
import java.util.*;

public class UncaughtException extends ExternalResource {
	private UncaughtExceptionHandler currentUncaughtExceptionHandler;
	final List<Throwable> uncaughtExceptions = Lists.newArrayList();

	@Override
	protected void before() throws Throwable {
		currentUncaughtExceptionHandler = Thread.currentThread().getUncaughtExceptionHandler();

		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread t, Throwable e) {
				uncaughtExceptions.add(e);
			}
		});
	}

	@Override
	protected void after() {
		Thread.setDefaultUncaughtExceptionHandler(currentUncaughtExceptionHandler);
	}

	public boolean hasUncaughtException() {
		return !uncaughtExceptions.isEmpty();
	}

	public Throwable firstUncaughtException() {
		return uncaughtExceptions.get(0);
	}
}
