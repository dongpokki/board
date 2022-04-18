package kr.spring.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.board.vo.BoardReplyVO;
import kr.spring.board.vo.BoardVO;

public interface BoardMapper {
	//부모글
	public List<BoardVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	@Insert("insert into spboard (board_num,title,content,uploadfile,filename,ip,mem_num) values (spboard_seq.nextval,#{title},#{content},#{uploadfile},#{filename},#{ip},#{mem_num})")
	public void insertBoard(BoardVO board);
	@Select("select * from spboard b join spmember m on b.mem_num = m.mem_num where b.board_num=#{board_num}")
	public BoardVO selectBoard(Integer board_num);
	@Update("update spboard set hit=hit+1 where board_num = #{board_num}")
	public void updateHit(Integer board_num);
	public void updateBoard(BoardVO board);
	@Delete("delete from spboard where board_num = #{board_num}")
	public void deleteBoard(Integer board_num);
	@Update("update spboard set uploadfile='',filename='' where board_num=#{board_num}")
	public void deleteFile(Integer board_num);
	
	//댓글
	public List<BoardReplyVO> selectListReply(Map<String,Object> map);
	@Select("select count(*) from spboard_reply b join spmember m on b.mem_num=m.mem_num where b.board_num=#{board_num}")
	public int selectRowCountReply(Map<String,Object> map);
	@Select("select * from spboard_reply where re_num=#{re_num}")
	public BoardReplyVO selectReply(Integer re_num);
	@Insert("insert into spboard_reply (re_num,re_content,re_ip,board_num,mem_num) values(spreply_seq.nextval,#{re_content},#{re_ip},#{board_num},#{mem_num})")
	public void insertReply(BoardReplyVO boardReply);
	@Update("update spboard_reply set re_content=#{re_content},re_ip=#{re_ip},re_mdate=sysdate where re_num=#{re_num}")
	public void updateReply(BoardReplyVO boardReply);
	@Delete("delete from spboard_reply where re_num=#{re_num}")
	public void deleteReply(Integer re_num);
	//부모글 삭제시 댓글이 존재하면 부모글 삭제전 댓글 삭제
	@Delete("delete from spboard_reply where board_num=#{board_num}")
	public void deleteReplyByBoardNum(Integer board_num);
	
}