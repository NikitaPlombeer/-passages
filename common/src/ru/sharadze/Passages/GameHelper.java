package ru.sharadze.Passages;


/**
* ru/sharadze/Passages/GameHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Passages.idl
* 24 апреля 2018 г. 16:28:23 MSK
*/

abstract public class GameHelper
{
  private static String  _id = "IDL:Passages/Game:1.0";

  public static void insert (org.omg.CORBA.Any a, Game that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Game extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (GameHelper.id (), "Game");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Game read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_GameStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Game value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static Game narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof Game)
      return (Game)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _GameStub stub = new _GameStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static Game unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof Game)
      return (Game)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _GameStub stub = new _GameStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}