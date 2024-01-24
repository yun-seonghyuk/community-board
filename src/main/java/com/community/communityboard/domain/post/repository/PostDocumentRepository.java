package com.community.communityboard.domain.post.repository;


import com.community.communityboard.domain.post.model.entity.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostDocumentRepository extends ElasticsearchRepository<PostDocument, Long> {

}
