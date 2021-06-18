package com.challenge.blog.converter;

import com.challenge.blog.Model.PostModel;
import com.challenge.blog.entity.Post;
import com.challenge.blog.repositories.PostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("PostConverter")
public class PostConverter extends Converter<PostModel, Post>{

    @Autowired
    private PostRepository webSettingRepository;

    public PostModel entityToModel(Post entity) {
        PostModel model = new PostModel();
        try {
            BeanUtils.copyProperties(entity, model);
        } catch (Exception e) {
            log.error("Error al convertir la entity en el modelo de la Foto", e);
        }

        return model;
    }

    public Post modelToEntity(PostModel model) {
        Post entity;
        if (model.getId() != null) {
            entity = webSettingRepository.getOne(model.getId());
        } else {
            entity = new Post();
        }

        try {
            BeanUtils.copyProperties(model, entity);
        } catch (Exception e) {
            log.error("Error al convertir el modelo de la Foto en entity", e);
        }

        return entity;
    }

    public List<PostModel> entitiesToModels(List<Post> entities) {
        List<PostModel> models = new ArrayList();
        for (Post a : entities) {
            models.add(entityToModel(a));
        }
        return models;
    }

    @Override
    public List<Post> modelsToEntities(List<PostModel> m) {
        List<Post> entities = new ArrayList();
        for (PostModel model : m) {
            entities.add(modelToEntity(model));
        }
        return entities;
    }


}
