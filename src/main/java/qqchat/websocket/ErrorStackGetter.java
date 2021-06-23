
package com.github.zyxgad.qqchat.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public final class ErrorStackGetter{
	public static String getErrorStack(Throwable e){
		ByteArrayOutputStream byteoutstream = new ByteArrayOutputStream(256);
		PrintStream pstream = new PrintStream(byteoutstream);
		e.printStackTrace(pstream);
		return byteoutstream.toString();
	}
}
