package ru.sharadze.Passages;


/**
* ru/sharadze/Passages/GamePOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Passages.idl
* 24 апреля 2018 г. 16:28:23 MSK
*/

public abstract class GamePOA extends org.omg.PortableServer.Servant
 implements ru.sharadze.Passages.GameOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("startGame", new Integer (0));
    _methods.put ("onlineCount", new Integer (1));
    _methods.put ("get", new Integer (2));
    _methods.put ("isMyTurn", new Integer (3));
    _methods.put ("addBorder", new Integer (4));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    Integer __method = (Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // Passages/Game/startGame
       {
         String id = in.read_string ();
         String $result = null;
         $result = this.startGame (id);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // Passages/Game/onlineCount
       {
         String gameId = in.read_string ();
         int $result = (int)0;
         $result = this.onlineCount (gameId);
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 2:  // Passages/Game/get
       {
         String gameId = in.read_string ();
         int $result[] = null;
         $result = this.get (gameId);
         out = $rh.createReply();
         ru.sharadze.Passages.BackupPathesStringsHelper.write (out, $result);
         break;
       }

       case 3:  // Passages/Game/isMyTurn
       {
         String id = in.read_string ();
         String gameId = in.read_string ();
         boolean $result = false;
         $result = this.isMyTurn (id, gameId);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 4:  // Passages/Game/addBorder
       {
         int x = in.read_long ();
         int y = in.read_long ();
         ru.sharadze.Passages.BorderType type = ru.sharadze.Passages.BorderTypeHelper.read (in);
         String id = in.read_string ();
         String gameId = in.read_string ();
         boolean $result = false;
         $result = this.addBorder (x, y, type, id, gameId);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:Passages/Game:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Game _this() 
  {
    return GameHelper.narrow(
    super._this_object());
  }

  public Game _this(org.omg.CORBA.ORB orb) 
  {
    return GameHelper.narrow(
    super._this_object(orb));
  }


} // class GamePOA