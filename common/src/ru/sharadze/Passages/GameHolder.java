package ru.sharadze.Passages;

/**
* ru/sharadze/Passages/GameHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Passages.idl
* 24 апреля 2018 г. 16:28:23 MSK
*/

public final class GameHolder implements org.omg.CORBA.portable.Streamable
{
  public Game value = null;

  public GameHolder ()
  {
  }

  public GameHolder (Game initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = GameHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    GameHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return GameHelper.type ();
  }

}
