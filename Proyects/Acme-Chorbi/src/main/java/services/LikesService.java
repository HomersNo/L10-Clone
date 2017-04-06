
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Chorbi;
import domain.Likes;
import repositories.LikesRepository;

@Service
@Transactional
public class LikesService {
	
	@Autowired
	private LikesRepository likesRepository;
	
	@Autowired
	private ChorbiService chorbiService;
	
	public LikesService(){
		super();
	}
	
	public Likes create(final int chorbiId) {
		Likes result;
		final Chorbi chorbi = chorbiService.findByPrincipal();
		final Chorbi liked = chorbiService.findOne(chorbiId);
		Assert.isNull(likesRepository.findOneByChorbiAndLiked(chorbi.getId(), liked.getId()));
		result = new Likes();
		result.setChorbi(chorbi);
		result.setLiked(liked);

		return result;
	}

	public Likes findOne(int likesId) {
		Assert.isTrue(likesId != 0);

		Likes result;

		result = likesRepository.findOne(likesId);
		Assert.notNull(result);

		return result;
	}

	public Likes save(Likes likes) {
		Assert.notNull(likes);
		Likes result;
		
		result = likesRepository.save(likes);
		
		return result;
	}

	public void delete(Likes likes) {
		Assert.notNull(likes);
		Assert.isTrue(likes.getId() != 0);
		Assert.isTrue(likesRepository.exists(likes.getId()));

		likesRepository.delete(likes);
	}
	
	public Collection<Likes> findAllByPrincipal(){
		Collection<Likes> result;
		final Chorbi chorbi = chorbiService.findByPrincipal();
		if(chorbi==null){
			result = null;
		}else{
			result = likesRepository.findAllByChorbiId(chorbi.getId());
		}
		return result;
	}

}
