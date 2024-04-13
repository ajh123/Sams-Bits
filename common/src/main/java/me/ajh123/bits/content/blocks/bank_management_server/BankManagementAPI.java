package me.ajh123.bits.content.blocks.bank_management_server;

import dan200.computercraft.api.lua.ILuaAPI;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;


public class BankManagementAPI implements ILuaAPI {
	@Override
	public String[] getNames() {
		return new String[]{"bank_management"};
	}

	@LuaFunction
	public final MethodResult isInstalled() {
		return MethodResult.of(true);
	}
}
