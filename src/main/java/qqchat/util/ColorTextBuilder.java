
package com.github.zyxgad.qqchat.util;

import org.bukkit.ChatColor;

public final class ColorTextBuilder{

	private final StringBuilder builder;

	public ColorTextBuilder(){
		this.builder = new StringBuilder();
	}

	public ColorTextBuilder(int len){
		this.builder = new StringBuilder(len);
	}

	/****************/
	public ColorTextBuilder add(char c){
		this.builder.append(c);
		return this;
	}

	public ColorTextBuilder add(byte num){
		this.builder.append(num);
		return this;
	}

	public ColorTextBuilder add(int num){
		this.builder.append(num);
		return this;
	}

	public ColorTextBuilder add(long num){
		this.builder.append(num);
		return this;
	}

	public ColorTextBuilder add(float num){
		this.builder.append(num);
		return this;
	}

	public ColorTextBuilder add(double num){
		this.builder.append(num);
		return this;
	}

	public ColorTextBuilder add(String str){
		this.builder.append(str);
		return this;
	}

	public ColorTextBuilder add(Object obj){
		this.builder.append(obj.toString());
		return this;
	}

	public ColorTextBuilder line(){
		return this.add('\n');
	}

	public ColorTextBuilder line(String str){
		return this.add(str).add('\n');
	}

	/****************/

	/* color */
	public ColorTextBuilder black(){
		return this.add(ChatColor.BLACK);
	}
	public ColorTextBuilder black(String str){
		return this.black().add(str).reset();
	}
	public ColorTextBuilder gray(){
		return this.add(ChatColor.GRAY);
	}
	public ColorTextBuilder gray(String str){
		return this.gray().add(str).reset();
	}
	public ColorTextBuilder white(){
		return this.add(ChatColor.WHITE);
	}
	public ColorTextBuilder white(String str){
		return this.white().add(str).reset();
	}
	public ColorTextBuilder gold(){
		return this.add(ChatColor.GOLD);
	}
	public ColorTextBuilder gold(String str){
		return this.gold().add(str).reset();
	}
	public ColorTextBuilder red(){
		return this.add(ChatColor.RED);
	}
	public ColorTextBuilder red(String str){
		return this.red().add(str).reset();
	}
	public ColorTextBuilder yellow(){
		return this.add(ChatColor.YELLOW);
	}
	public ColorTextBuilder yellow(String str){
		return this.yellow().add(str).reset();
	}
	public ColorTextBuilder green(){
		return this.add(ChatColor.GREEN);
	}
	public ColorTextBuilder green(String str){
		return this.green().add(str).reset();
	}
	public ColorTextBuilder aqua(){
		return this.add(ChatColor.AQUA);
	}
	public ColorTextBuilder aqua(String str){
		return this.aqua().add(str).reset();
	}
	public ColorTextBuilder blue(){
		return this.add(ChatColor.BLUE);
	}
	public ColorTextBuilder blue(String str){
		return this.blue().add(str).reset();
	}
	public ColorTextBuilder purple(){
		return this.add(ChatColor.LIGHT_PURPLE);
	}
	public ColorTextBuilder purple(String str){
		return this.purple().add(str).reset();
	}
	/* */

	/* dark color */
	public ColorTextBuilder dgray(){
		return this.add(ChatColor.DARK_GRAY);
	}
	public ColorTextBuilder dgray(String str){
		return this.dgray().add(str).reset();
	}
	public ColorTextBuilder dred(){
		return this.add(ChatColor.DARK_RED);
	}
	public ColorTextBuilder dred(String str){
		return this.dred().add(str).reset();
	}
	public ColorTextBuilder dyellow(){
		return this.add(ChatColor.YELLOW);
	}
	public ColorTextBuilder dyellow(String str){
		return this.dyellow().add(str).reset();
	}
	public ColorTextBuilder dgreen(){
		return this.add(ChatColor.DARK_GREEN);
	}
	public ColorTextBuilder dgreen(String str){
		return this.dgreen().add(str).reset();
	}
	public ColorTextBuilder daqua(){
		return this.add(ChatColor.DARK_AQUA);
	}
	public ColorTextBuilder daqua(String str){
		return this.daqua().add(str).reset();
	}
	public ColorTextBuilder dblue(){
		return this.add(ChatColor.DARK_BLUE);
	}
	public ColorTextBuilder dblue(String str){
		return this.dblue().add(str).reset();
	}
	public ColorTextBuilder dpurple(){
		return this.add(ChatColor.DARK_PURPLE);
	}
	public ColorTextBuilder dpurple(String str){
		return this.dpurple().add(str).reset();
	}
	/* */

	/* magic word */
	public ColorTextBuilder magic(){
		return this.add(ChatColor.MAGIC);
	}
	public ColorTextBuilder magic(String str){
		return this.magic().add(str).reset();
	}
	public ColorTextBuilder blod(){
		return this.add(ChatColor.BOLD);
	}
	public ColorTextBuilder blod(String str){
		return this.blod().add(str).reset();
	}
	public ColorTextBuilder striket(){
		return this.add(ChatColor.STRIKETHROUGH);
	}
	public ColorTextBuilder striket(String str){
		return this.striket().add(str).reset();
	}
	public ColorTextBuilder under(){
		return this.add(ChatColor.UNDERLINE);
	}
	public ColorTextBuilder under(String str){
		return this.under().add(str).reset();
	}
	public ColorTextBuilder italic(){
		return this.add(ChatColor.ITALIC);
	}
	public ColorTextBuilder italic(String str){
		return this.italic().add(str).reset();
	}
	public ColorTextBuilder reset(){
		return this.add(ChatColor.RESET);
	}
	public ColorTextBuilder reset(String str){
		return this.reset().add(str);
	}
	/* */

	public int length(){
		return this.builder.length();
	}

	@Override
	public String toString(){
		return this.builder.toString();
	}

	public String toWhiteString(){
		StringBuilder colorStr = new StringBuilder(this.builder);
		int n;
		while((n = colorStr.indexOf(String.valueOf(ChatColor.COLOR_CHAR))) != -1){
			colorStr.delete(n, n + 2);
		}
		return colorStr.toString();
	}
}