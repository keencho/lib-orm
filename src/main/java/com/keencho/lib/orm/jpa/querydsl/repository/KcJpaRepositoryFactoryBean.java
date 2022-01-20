package com.keencho.lib.orm.jpa.querydsl.repository;

import com.keencho.lib.orm.jpa.querydsl.KcSearchQuery;
import com.keencho.lib.orm.jpa.querydsl.KcSearchQueryImpl;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition.RepositoryFragments;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.core.support.RepositoryFragment;

import javax.persistence.EntityManager;

// https://stackoverflow.com/questions/53083047/replacing-deprecated-querydsljparepository-with-querydsljpapredicateexecutor-fai/53960209#53960209
// https://stackoverflow.com/questions/65018796/why-do-i-need-a-fragment-interface-for-repositories-that-stand-on-their-own
public class KcJpaRepositoryFactoryBean<T extends Repository<S, ID>, S, ID> extends JpaRepositoryFactoryBean<T, S, ID> {
    public KcJpaRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new KcJpaRepositoryFactory(entityManager);
    }

    private static class KcJpaRepositoryFactory extends JpaRepositoryFactory {
        private final EntityManager entityManager;

        public KcJpaRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.entityManager = entityManager;
        }

        @Override
        protected RepositoryFragments getRepositoryFragments(RepositoryMetadata metadata) {
            RepositoryFragments fragments = super.getRepositoryFragments(metadata);

            if (KcSearchQuery.class.isAssignableFrom(metadata.getRepositoryInterface())) {
                JpaEntityInformation<?, ?> entityInformation = this.getEntityInformation(metadata.getDomainType());

                Object impl = super.instantiateClass(
                        KcSearchQueryImpl.class,
                        entityInformation, this.entityManager
                );

                fragments = fragments.append(RepositoryFragment.implemented(impl));
            }

            return fragments;
        }
    }
}
