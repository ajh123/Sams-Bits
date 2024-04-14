package me.ajh123.bits.content.blocks.bank_management_server;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static me.ajh123.bits.SamsBits.modLoc;


public class BankManagementAPI implements IPeripheral {
	private final BankManagementServerBlockEntity owner;

	public BankManagementAPI(BankManagementServerBlockEntity owner) {
		this.owner = owner;
	}

	@LuaFunction
	public final MethodResult isInstalled() {
		return MethodResult.of(true);
	}

	@Override
	public String getType() {
		return modLoc("bank_management").toString();
	}

	@Override
	public boolean equals(@Nullable IPeripheral other) {
		return this.equals((Object) other);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BankManagementAPI that = (BankManagementAPI) o;
		return Objects.equals(owner, that.owner);
	}

	@Override
	public int hashCode() {
		return Objects.hash(owner);
	}
}
