package org.osj.gemhuntplugin.DATAMANAGE;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.osj.gemhuntplugin.GemHuntPlugin;
import org.osj.gemhuntplugin.USER.User;

import java.sql.*;
import java.util.UUID;

public class DB_Connect
{
    private static DB_Connect instance;

    public static DB_Connect getInstance()
    {
        if(instance == null)
        {
            synchronized (DB_Connect.class)
            {
                instance = new DB_Connect();
            }
        }

        return instance;
    }

    private Connection connection;

    private String host = "localhost";
    private int port = 4507;
    private String database = "mc_gemhunt";
    private String table = "userdata";
    private String username = "root";
    private String password = "vu64kH!M*W";

    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public Connection open_Connection()
    {
        try
        {
            if (connection != null && !connection.isClosed())
            {
                return null;
            }

            synchronized (this)
            {
                if (connection != null && !connection.isClosed())
                {
                    return null;
                }
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
                if(connection == null)
                {
                    Bukkit.shutdown();
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return connection;
    }

    public void SetUUID(UUID uuid)
    {
        String coloumName = "u_uuid";
        SetData(uuid, coloumName, uuid.toString());
    }
    public void SetName(UUID uuid, String name)
    {
        String coloumName = "u_name";
        SetData(uuid, coloumName, name);
    }
    public void SetStat(UUID uuid, String stat, Integer lv)
    {
        String coloumName = "u_" + stat;
        SetData(uuid, coloumName, lv);
    }

    public int insertMember(Player player)
    {
        // 예외 처리 안해주고 오류 발생하면 서버 터짐
        Connection conn = null;
        try
        {
            conn = this.open_Connection();
            // 사용자의 정보가 있는지 없는지 확인하는 질의문을 생성한다.
            String sql = "select count(*) from " + table + " u where u.u_uuid = ?";
            // PreparedStatement에 질의어를 넣고
            pstmt = conn.prepareStatement(sql);
            // 질의어에 ?라고 적혀 있는 값을 정의 해준다.
            pstmt.setString(1, player.getUniqueId().toString());
            // 질의어 실행
            rs = pstmt.executeQuery();
            // 검색된 레코드를 넘겨줌 (필수)
            rs.next();
            // 해당하는 유저의 정보가 없는 경우
            if (rs.getInt(1) == 0)
            {
                try
                {
                    // PreparedStatement 초기화 (재사용을 위해)
                    pstmt.clearParameters();
                    sql = "INSERT INTO " + table.toUpperCase() + " (u_uuid, u_name, u_discovery, u_plenty, u_treasure, u_physical) VALUES (?, ?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, player.getUniqueId().toString()); // uuid
                    pstmt.setString(2, player.getName()); // name
                    pstmt.setInt(3, 0); // discovery level
                    pstmt.setInt(4, 0); // plenty level
                    pstmt.setInt(5, 0); // treasure level
                    pstmt.setInt(6, 0); // physical level
                    pstmt.executeUpdate();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return 0;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 1;
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return 2;
            }
        }
    }

    public User DB_PlayerInfo(UUID uuid)
    {
        User user = GemHuntPlugin.getUserManager().getUserData(uuid);
        Connection conn = null;
        // 예외 처리 안해주면 서버 터짐
        try
        {
            conn = this.open_Connection();
            // 사용자의 정보가 있는지 없는지 확인하는 질의문을 생성한다.
            String sql = "select u.u_no, u.u_uuid, u.u_name, u.u_discovery, u.u_plenty, u.u_treasure, u.u_physical from " + table + " u where u.u_uuid = ?";
            // PreparedStatement에 질의어를 넣고
            pstmt = conn.prepareStatement(sql);
            // 질의어에 ?라고 적혀 있는 값을 정의 해준다.
            pstmt.setString(1, uuid.toString());
            // 질의어 실행
            rs = pstmt.executeQuery();
            // 검색된 레코드를 넘겨줌 (필수임 ㅇ)
            rs.next();

            user.loadUUID(UUID.fromString(rs.getString("u_uuid")));
            user.loadName(rs.getString("u_name"));
            user.loadDiscovery(rs.getInt("u_discovery"));
            user.loadPlenty(rs.getInt("u_plenty"));
            user.loadTreasure(rs.getInt("u_treasure"));
            user.loadPhysical(rs.getInt("u_physical"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                // 연결 된 DB 연결을 종료 해준다. 안해주면 여러개 쌓이면 DB 검색 안됨 (즉, commit과 같은 용도)
                conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return user;
    }

    private int SetData(UUID uuid, String coloum, Integer content)
    {
        // 예외 처리 안해주고 오류 발생하면 서버 터짐
        Connection conn = null;
        try
        {
            conn = this.open_Connection();
            // 사용자의 정보가 있는지 없는지 확인하는 질의문을 생성한다.
            String sql = "select count(*) from " + table + " u where u.u_uuid = ?";
            // PreparedStatement에 질의어를 넣고
            pstmt = conn.prepareStatement(sql);
            // 질의어에 ?라고 적혀 있는 값을 정의 해준다.
            pstmt.setString(1, uuid.toString());
            // 질의어 실행
            rs = pstmt.executeQuery();
            // 검색된 레코드를 넘겨줌 (필수)
            rs.next();
            // 해당하는 유저의 정보가 없는 경우
            try
            {
                pstmt.clearParameters();
                sql = "UPDATE " + table + " SET " + coloum +  " = " + content + " WHERE u_uuid = \"" + uuid + "\"";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return 0;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 1;
        }
        finally
        {
            try
            {
                conn.close();
                DB_PlayerInfo(uuid);
                //PlayerScoreBoard.setScoreboard(player);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return 2;
            }
        }
    }

    private int SetData(UUID uuid, String coloum, String content)
    {
        // 예외 처리 안해주고 오류 발생하면 서버 터짐
        Connection conn = null;
        try
        {
            conn = this.open_Connection();
            // 사용자의 정보가 있는지 없는지 확인하는 질의문을 생성한다.
            String sql = "select count(*) from " + table + " u where u.u_uuid = ?";
            // PreparedStatement에 질의어를 넣고
            pstmt = conn.prepareStatement(sql);
            // 질의어에 ?라고 적혀 있는 값을 정의 해준다.
            pstmt.setString(1, uuid.toString());
            // 질의어 실행
            rs = pstmt.executeQuery();
            // 검색된 레코드를 넘겨줌 (필수)
            rs.next();
            // 해당하는 유저의 정보가 없는 경우
            try
            {
                pstmt.clearParameters();
                sql = "UPDATE " + table + " SET " + coloum +  " = " + content + " WHERE u_uuid = \"" + uuid + "\"";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return 0;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 1;
        }
        finally
        {
            try
            {
                conn.close();
                DB_PlayerInfo(uuid);
                //PlayerScoreBoard.setScoreboard(player);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return 2;
            }
        }
    }
}
